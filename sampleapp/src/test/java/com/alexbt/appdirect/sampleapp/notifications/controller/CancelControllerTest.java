package com.alexbt.appdirect.sampleapp.notifications.controller;

import static org.mockito.Mockito.verify;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.alexbt.appdirect.notifications.model.enu.Type;

@WebMvcTest(CancelController.class)
public class CancelControllerTest extends AbstractExistingControllerTest {

    @Override
    protected void verifyServiceInteractions() {
        verify(subscriptionService).cancel(notification, notification.getPayload().getAccount());
    }

    @Override
    protected Type getType() {
        return Type.SUBSCRIPTION_CANCEL;
    }
}
