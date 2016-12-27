package com.alexbt.appdirect.notifications.model;

import org.junit.Test;

public class PayloadOrderValidationTest extends AbstractValidationTest {

    @Test
    public void testAddonOfferingCodeNullable() {
        notification.getPayload().getOrder().setAddonOfferingCode(null);
        testNotificationIsValid(notification);
    }

    @Test
    public void testEditionCodeNotNullable() {
        notification.getPayload().getOrder().setEditionCode(null);
        testNullField("payload.order.editionCode");
    }

    @Test
    public void testPricingDurationNotNullable() {
        notification.getPayload().getOrder().setPricingDuration(null);
        testNullField("payload.order.pricingDuration");
    }
}