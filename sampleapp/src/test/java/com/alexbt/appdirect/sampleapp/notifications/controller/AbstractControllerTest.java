package com.alexbt.appdirect.sampleapp.notifications.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alexbt.appdirect.notifications.model.Account;
import com.alexbt.appdirect.notifications.model.Notice;
import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.Payload;
import com.alexbt.appdirect.notifications.model.User;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.alexbt.appdirect.notifications.model.response.ErrorCode;
import com.alexbt.appdirect.sampleapp.notifications.dao.SubscriptionRepository;
import com.alexbt.appdirect.sampleapp.notifications.exception.InvalidResponseException;
import com.alexbt.appdirect.sampleapp.notifications.exception.UnauthorizedException;
import com.alexbt.appdirect.sampleapp.notifications.model.Subscription;
import com.alexbt.appdirect.sampleapp.notifications.service.SubscriptionService;
import com.alexbt.appdirect.sampleapp.notifications.util.NotificationValidate;
import com.alexbt.appdirect.sampleapp.notifications.util.TestErrorResponse;
import com.alexbt.appdirect.sampleapp.util.WebConstants;
import com.alexbt.autoconfigure.oauth.controller.TwoLeggedControllerHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WithMockUser(authorities = WebConstants.MARKETPLACE_USER)
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected NotificationValidate notificationValidate;

    @MockBean
    protected SubscriptionService subscriptionService;

    @MockBean
    protected Notification notification;

    @MockBean
    private TwoLeggedControllerHelper twoLeggedControllerHelper;

    @MockBean
    private SubscriptionRepository subscriptionRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    protected String getUrl() {
        Class<?> controllerBeingTested = this.getClass().getAnnotation(WebMvcTest.class).value()[0];
        String apiUrl = controllerBeingTested.getAnnotation(RequestMapping.class).value()[0];
        return apiUrl;
    }

    @Before
    public void before() throws URISyntaxException {
        given(notification.getPayload()).willReturn(mock(Payload.class));
        given(notification.getPayload().getAccount()).willReturn(mock(Account.class));
        given(notification.getPayload().getAccount().getAccountIdentifier()).willReturn("accId");
        given(notification.getPayload().getNotice()).willReturn(mock(Notice.class));
        given(notification.getCreator()).willReturn(mock(User.class));

        given(twoLeggedControllerHelper.twoLeggedExchange(any(URL.class), any(Class.class))).willReturn(notification);
    }

    @Test
    public void testBadFormat() throws Exception {
        doThrow(new InvalidResponseException("msg")).when(notificationValidate).hasAllRequiredFields(any(Notification.class));

        assertErrorResponse(ErrorCode.INVALID_RESPONSE, mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy")));
    }

    @Test
    public void testBadUrlParam() throws Exception {
        assertErrorResponse(ErrorCode.UNKNOWN_ERROR, mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=dummy")));
    }

    // sanity check
    @Test
    public void testWorksWithoutMock() throws Exception {
        assertSuccessResponse(mockMvc.perform(MockMvcRequestBuilders.get(getUrl() + "?url=http://blah.com/dummy")));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRuntimeExceptionInFw() throws Exception {
        given(twoLeggedControllerHelper.twoLeggedExchange(any(URL.class), any(Class.class))) //
                .willThrow(NullPointerException.class);

        assertErrorResponse(ErrorCode.UNKNOWN_ERROR, mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy")));
    }

    @Test
    public void testRuntimeExceptionValidate() throws Exception {
        doThrow(NullPointerException.class).when(notificationValidate).hasAllRequiredFields(any(Notification.class));

        assertErrorResponse(ErrorCode.UNKNOWN_ERROR, mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy")));
    }

    @Test
    public void testMonitoring() throws Exception {
        doThrow(new UnauthorizedException("msg")).when(notificationValidate).isNotStateless(anyObject());

        assertErrorResponse(ErrorCode.UNAUTHORIZED, mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy")));
    }

    @Test
    public void testForbidden1() throws Exception {
        doThrow(new AccessDeniedException("msg")).when(notificationValidate).isNotStateless(anyObject());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy"));

        result.andExpect(status().isUnauthorized());
    }

    // This test raised a 302
    // BUT, with OAuth Spring Configuration, 401 is rather thrown.
    @Test
    @WithAnonymousUser
    public void testForbidden2() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy"));

        result.andExpect(status().is3xxRedirection());
    }

    @Test
    public void testRuntimeExceptionRepository() throws Exception {
        doThrow(NullPointerException.class).when(subscriptionService).create(any(Notification.class), any(Account.class));
        doThrow(NullPointerException.class).when(subscriptionService).addUser(any(Notification.class), any(Account.class));
        doThrow(NullPointerException.class).when(subscriptionService).cancel(any(Notification.class), any(Account.class));
        doThrow(NullPointerException.class).when(subscriptionService).removeUser(any(Notification.class), any(Account.class));
        doThrow(NullPointerException.class).when(subscriptionService).notice(any(Notification.class), any(Account.class));
        doThrow(NullPointerException.class).when(subscriptionService).update(any(Notification.class), any(Account.class));

        assertErrorResponse(ErrorCode.UNKNOWN_ERROR, mockMvc.perform(MockMvcRequestBuilders.get(getUrl() + "?url=http://blah.com/dummy")));
    }

    @Test
    public void testInvalidNotificationType() throws Exception {
        given(notification.getType()).willReturn(Type.USER_UPDATED);
        doThrow(new InvalidResponseException("msg")).when(notificationValidate).isExpectedType(any(Type.class), any(Type.class));

        assertErrorResponse(ErrorCode.INVALID_RESPONSE, mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy")));
    }

    protected void assertErrorResponse(ErrorCode errorCode, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk());
        TestErrorResponse response = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), TestErrorResponse.class);
        assertEquals(false, response.isSuccess());
        assertEquals(errorCode, response.getErrorCode());
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    protected void assertSuccessResponse(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk());
        TestErrorResponse response = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), TestErrorResponse.class);
        assertEquals(true, response.isSuccess());
        assertEquals(null, response.getErrorCode());
        assertTrue(response.getAccountIdentifier() != null);

        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

}
