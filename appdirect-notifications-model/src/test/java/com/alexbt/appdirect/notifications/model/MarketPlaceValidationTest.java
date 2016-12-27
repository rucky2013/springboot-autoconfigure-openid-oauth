package com.alexbt.appdirect.notifications.model;

import org.junit.Test;

public class MarketPlaceValidationTest extends AbstractValidationTest {

    @Test
    public void testPartnerNotNullable() {
        notification.getMarketplace().setPartner(null);
        testNullField("marketplace.partner");
    }

    @Test
    public void testBaseUrlNotNullable() {
        notification.getMarketplace().setBaseUrl(null);
        testNullField("marketplace.baseUrl");
    }
}
