package com.alexbt.appdirect.notifications.model;

import org.junit.Test;

public class PayloadUserValidationTest extends AbstractValidationTest {

    @Test
    public void testUserAddressNullable() {
        notification.getPayload().getUser().setAddress(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserAttributesNullable() {
        notification.getPayload().getUser().setAttributes(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserEmailNotNullable() {
        notification.getPayload().getUser().setEmail(null);
        testNullField("payload.user.email");
    }

    @Test
    public void testUserFirstNameNullable() {
        notification.getPayload().getUser().setFirstName(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserLanguageNullable() {
        notification.getPayload().getUser().setLanguage(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserLastNameNullable() {
        notification.getPayload().getUser().setLastName(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserLocaleNullable() {
        notification.getPayload().getUser().setLocale(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserOpenIdNullable() {
        notification.getPayload().getUser().setOpenId(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserUuidNullable() {
        notification.getPayload().getUser().setUuid(null);
        testNotificationIsValid(notification);
    }
}
