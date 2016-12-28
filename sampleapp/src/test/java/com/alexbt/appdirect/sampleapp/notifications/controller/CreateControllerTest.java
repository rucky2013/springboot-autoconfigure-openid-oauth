package com.alexbt.appdirect.sampleapp.notifications.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alexbt.appdirect.notifications.model.Account;
import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.User;
import com.alexbt.appdirect.notifications.model.response.ErrorCode;
import com.alexbt.appdirect.sampleapp.notifications.exception.UnauthorizedException;
import com.alexbt.appdirect.sampleapp.notifications.exception.UserAlreadyExistException;

@WebMvcTest(CreateController.class)
public class CreateControllerTest extends AbstractControllerTest {

    @Test
    public void testNoErrorSanityCheck() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy"));

        verify(subscriptionService).create(any(Notification.class), any(Account.class));
        verifyNoMoreInteractions(subscriptionService);
    }

    @Test
    public void testAccountFound() throws Exception {
        doThrow(new UnauthorizedException("message")).when(notificationValidate).isNewAccount(any(Account.class));

        assertErrorResponse(ErrorCode.UNAUTHORIZED, mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy")));
    }

    @Test
    public void testUserAlreadyExist() throws Exception {
        doThrow(new UserAlreadyExistException("message")).when(notificationValidate).isNewUser(any(User.class));

        assertErrorResponse(ErrorCode.USER_ALREADY_EXISTS, mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy")));
    }

}
