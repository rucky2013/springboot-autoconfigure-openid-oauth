package com.alexbt.appdirect.notifications.model;

import org.junit.Test;

public class NotificationValidationTest extends AbstractValidationTest {

    @Test
    public void testMarketPlaceNull() {
        notification.setMarketplace(null);
        testNullField("marketplace");
    }

    @Test
    public void testTypeNull() {
        notification.setType(null);
        testNullField("type");
    }

    @Test
    public void testApplicationUuidNullable() {
        notification.setApplicationUuid(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testCreatorNullable() {
        notification.setCreator(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testFlagNullable() {
        notification.setFlag(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testPayloadNotNullable() {
        notification.setPayload(null);
        testNullField("payload");
    }

    @Test
    public void testReturnUrlNullable() {
        notification.setReturnUrl(null);
        testNotificationIsValid(notification);
    }
}
