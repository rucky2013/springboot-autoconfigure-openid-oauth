package com.alexbt.appdirect.sampleapp.notifications.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alexbt.appdirect.notifications.model.User;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.alexbt.appdirect.notifications.model.response.ErrorCode;
import com.alexbt.appdirect.sampleapp.notifications.exception.UserNotFoundException;

@WebMvcTest(UserUnassignController.class)
public class UserUnassignControllerTest extends AbstractExistingControllerTest {

    @Test
    public void testUserNotExist() throws Exception {
        doThrow(new UserNotFoundException("message")).when(notificationValidate).userExists(any(User.class));

        assertControllerReturns(ErrorCode.USER_NOT_FOUND, mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy")));
    }

    @Override
    protected void verifyServiceInteractions() {
        verify(subscriptionService).removeUser(notification, notification.getPayload().getAccount());
    }

    @Override
    protected Type getType() {
        return Type.USER_UNASSIGNMENT;
    }
}
