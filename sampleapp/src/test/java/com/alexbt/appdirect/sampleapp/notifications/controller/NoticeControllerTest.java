package com.alexbt.appdirect.sampleapp.notifications.controller;

import static org.mockito.Mockito.verify;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.alexbt.appdirect.notifications.model.enu.Type;

@WebMvcTest(NoticeController.class)
public class NoticeControllerTest extends AbstractExistingControllerTest {

    @Override
    protected void verifyServiceInteractions() {
        verify(subscriptionService).notice(notification, notification.getPayload().getAccount());
    }

    @Override
    protected Type getType() {
        return Type.SUBSCRIPTION_NOTICE;
    }

}
