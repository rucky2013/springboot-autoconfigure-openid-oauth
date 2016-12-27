package com.alexbt.appdirect.notifications.model;

import org.junit.Test;

public class PayloadAccountValidationTest extends AbstractValidationTest {

    @Test
    public void testAccountNotNullable() {
        notification.getPayload().getAccount().setAccountIdentifier(null);
        testNullField("payload.account.accountIdentifier");
    }

    @Test
    public void testStatusNotNullable() {
        notification.getPayload().getAccount().setStatus(null);
        testNullField("payload.account.status");
    }
}