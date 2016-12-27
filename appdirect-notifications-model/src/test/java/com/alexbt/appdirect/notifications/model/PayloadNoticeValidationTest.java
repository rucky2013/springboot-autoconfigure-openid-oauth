package com.alexbt.appdirect.notifications.model;

import org.junit.Test;

public class PayloadNoticeValidationTest extends AbstractValidationTest {

    @Test
    public void testMessageNullable() {
        notification.getPayload().getNotice().setMessage(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testTypeNotNullable() {
        notification.getPayload().getNotice().setType(null);
        testNullField("payload.notice.type");
    }
}