package com.alexbt.appdirect.sampleapp.notifications.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.enu.Flag;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.alexbt.appdirect.notifications.model.response.ErrorCode;
import com.alexbt.appdirect.sampleapp.notifications.controller.CreateController;
import com.alexbt.appdirect.sampleapp.notifications.dao.SubscriptionRepository;
import com.alexbt.appdirect.sampleapp.notifications.service.SubscriptionService;
import com.alexbt.appdirect.sampleapp.notifications.util.NotificationValidate;
import com.alexbt.appdirect.sampleapp.notifications.util.TestErrorResponse;
import com.alexbt.appdirect.sampleapp.util.WebConstants;
import com.alexbt.autoconfigure.oauth.controller.TwoLeggedControllerHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

//Technically, the exceptions are tested through the Controller tests.
//However, this tests is a sanity check (using Reflection) that all exceptions are tested and caught.
@RunWith(SpringRunner.class)
@WebMvcTest(CreateController.class)
@WithMockUser(authorities = WebConstants.MARKETPLACE_USER)
@MockBeans({ @MockBean(SubscriptionRepository.class), @MockBean(SubscriptionService.class) })
public class AllExceptionCaughtTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TwoLeggedControllerHelper twoLeggedControllerHelper;

    @MockBean
    private NotificationValidate notificationValidate;

    @Spy
    private GenericExceptionHandler genericExceptionHandler = new GenericExceptionHandler();

    @MockBean
    private Notification notification;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before() throws URISyntaxException {
        given(twoLeggedControllerHelper.twoLeggedExchange(any(URL.class), any(Class.class))).willReturn(notification);
    }

    @Test
    public void testAllNotificationException() throws Exception {
        given(notification.getType()).willReturn(Type.SUBSCRIPTION_ORDER);
        given(twoLeggedControllerHelper.twoLeggedExchange(any(URL.class), any(Class.class))).willReturn(notification);

        Reflections r = new Reflections(NotificationException.class.getPackage().getName());
        Set<Class<? extends NotificationException>> subTypes = r.getSubTypesOf(NotificationException.class);

        // sanity
        assertTrue(subTypes.size() >= 5);

        for (Class<? extends NotificationException> exception : subTypes) {
            NotificationException newInstance = exception.getConstructor(String.class).newInstance("msg");
            doThrow(newInstance).when(notificationValidate).isNotStateless(any(Flag.class));
            String uri = CreateController.class.getAnnotation(RequestMapping.class).value()[0];
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uri + "?url=http://blah.com/dummy"));

            assertResponseErrorCode(newInstance.getErrorCode(), resultActions);
        }
    }

    private void assertResponseErrorCode(ErrorCode errorCode, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk());
        TestErrorResponse response = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), TestErrorResponse.class);
        assertEquals(false, response.isSuccess());
        assertEquals(errorCode, response.getErrorCode());
    }

    @Test
    public void testNullPointerException() throws Exception {
        otherExceptionCaught(NullPointerException.class);
    }

    @Test
    public void testIllegalArgumentException() throws Exception {
        otherExceptionCaught(IllegalArgumentException.class);
    }

    @Test
    public void testAnyOtherRuntimeException() throws Exception {
        otherExceptionCaught(RuntimeException.class);
    }

    @Test
    public void testAccessDeniedException() throws Exception {
        doThrow(AccessDeniedException.class).when(notificationValidate).hasAllRequiredFields(any(Notification.class));
        String uri = CreateController.class.getAnnotation(RequestMapping.class).value()[0];
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uri + "?url=http://blah.com/dummy"));

        resultActions.andExpect(status().isUnauthorized());
    }

    private void otherExceptionCaught(Class<? extends RuntimeException> exception) throws Exception {
        doThrow(exception).when(notificationValidate).isNotStateless(any(Flag.class));
        String uri = CreateController.class.getAnnotation(RequestMapping.class).value()[0];
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uri + "?url=http://blah.com/dummy"));

        assertResponseErrorCode(ErrorCode.UNKNOWN_ERROR, resultActions);
    }
}
