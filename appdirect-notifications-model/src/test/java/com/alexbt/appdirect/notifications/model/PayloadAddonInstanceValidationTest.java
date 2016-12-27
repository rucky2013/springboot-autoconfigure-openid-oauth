package com.alexbt.appdirect.notifications.model;

import org.junit.Test;

public class PayloadAddonInstanceValidationTest extends AbstractValidationTest {

    @Test
    public void testAddonInstanceIdNotNullable() {
        notification.getPayload().getAddonInstance().setId(null);
        testNullField("payload.addonInstance.id");
    }
}