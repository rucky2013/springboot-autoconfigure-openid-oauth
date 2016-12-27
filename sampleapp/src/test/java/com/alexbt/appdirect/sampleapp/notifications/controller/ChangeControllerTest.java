package com.alexbt.appdirect.sampleapp.notifications.controller;

import static org.mockito.Mockito.verify;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.alexbt.appdirect.notifications.model.enu.Type;

@WebMvcTest(ChangeController.class)
public class ChangeControllerTest extends AbstractExistingControllerTest {

    @Override
    protected void verifyServiceInteractions() {
        verify(subscriptionService).update(notification, notification.getPayload().getAccount());
    }

    @Override
    protected Type getType() {
        return Type.SUBSCRIPTION_CHANGE;
    }

}
