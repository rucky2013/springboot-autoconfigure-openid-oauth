package com.alexbt.appdirect.sampleapp.notifications.controller;

import static org.mockito.Mockito.verify;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.alexbt.appdirect.notifications.model.enu.Type;

@WebMvcTest(UserAssignController.class)
public class UserAssignControllerTest extends AbstractExistingControllerTest {

    @Override
    protected void verifyServiceInteractions() {
        verify(subscriptionService).addUser(notification, notification.getPayload().getAccount());
    }

    @Override
    protected Type getType() {
        return Type.USER_ASSIGNMENT;
    }
}
