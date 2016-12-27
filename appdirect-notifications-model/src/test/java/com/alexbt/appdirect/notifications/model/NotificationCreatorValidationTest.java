package com.alexbt.appdirect.notifications.model;

import org.junit.Test;

public class NotificationCreatorValidationTest extends AbstractValidationTest {

    @Test
    public void testUserAddressNullable() {
        notification.getCreator().setAddress(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserAttributesNullable() {
        notification.getCreator().setAttributes(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserEmailNotNullable() {
        notification.getCreator().setEmail(null);
        testNullField("creator.email");
    }

    @Test
    public void testUserFirstNameNullable() {
        notification.getCreator().setFirstName(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserLanguageNullable() {
        notification.getCreator().setLanguage(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserLastNameNullable() {
        notification.getCreator().setLastName(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserLocaleNullable() {
        notification.getCreator().setLocale(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserOpenIdNullable() {
        notification.getCreator().setOpenId(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testUserUuidNullable() {
        notification.getCreator().setUuid(null);
        testNotificationIsValid(notification);
    }
}
