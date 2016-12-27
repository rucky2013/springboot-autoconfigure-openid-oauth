package com.alexbt.appdirect.notifications.model;

import java.util.EnumSet;

import org.junit.Test;

import com.alexbt.appdirect.notifications.model.enu.Type;

public class PayloadValidationTest extends AbstractValidationTest {

    @Test
    public void testAccountNullableWhenCreate() {
        notification.setType(Type.SUBSCRIPTION_ORDER);
        notification.getPayload().setAccount(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testAccountNotNullableWhenChange() {
        notification.setType(Type.SUBSCRIPTION_CHANGE);
        notification.getPayload().setAccount(null);
        testNullField("payload.account");
    }

    @Test
    public void testAccountNullableWhenCancel() {
        notification.setType(Type.SUBSCRIPTION_CANCEL);
        notification.getPayload().setAccount(null);
        testNullField("payload.account");
    }

    @Test
    public void testAccountNullable() {
        notification.setType(Type.SUBSCRIPTION_NOTICE);
        notification.getPayload().setAccount(null);
        testNullField("payload.account");
    }

    @Test
    public void testAddonInstanceNullable() {
        notification.getPayload().setAddonInstance(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testCompanyNullableEvenForSubscriptionOrder() {
        notification.setType(Type.SUBSCRIPTION_ORDER);
        notification.getPayload().setCompany(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testCompanyNullableWhenNotSubscriptionOrder() {
        notification.getPayload().setCompany(null);
        EnumSet<Type> allOf = EnumSet.allOf(Type.class);
        allOf.remove(Type.SUBSCRIPTION_ORDER);
        for (Type type : allOf) {
            notification.setType(type);
            testNotificationIsValid(notification);
        }
    }

    @Test
    public void testNoticeNotNullable() {
        notification.setType(Type.SUBSCRIPTION_NOTICE);
        notification.getPayload().setNotice(null);
        testNullField("payload.notice");
    }

    @Test
    public void testOrderNotNullableWhenNotSubscriptionChange() {
        notification.setType(Type.SUBSCRIPTION_CHANGE);
        notification.getPayload().setOrder(null);
        testNullField("payload.order");
    }

    @Test
    public void testOrderNotNullableWhenNotSubscriptionOrder() {
        notification.setType(Type.SUBSCRIPTION_ORDER);
        notification.getPayload().setOrder(null);
        testNullField("payload.order");
    }

    @Test
    public void testOrderNullableWhenNotOrderOrChange() {
        notification.getPayload().setOrder(null);
        EnumSet<Type> allOf = EnumSet.allOf(Type.class);
        allOf.remove(Type.SUBSCRIPTION_ORDER);
        allOf.remove(Type.SUBSCRIPTION_CHANGE);
        for (Type type : allOf) {
            notification.setType(type);
            testNotificationIsValid(notification);
        }
    }

    @Test
    public void testUserNullable() {
        notification.getPayload().setUser(null);
        testNotificationIsValid(notification);
    }
}