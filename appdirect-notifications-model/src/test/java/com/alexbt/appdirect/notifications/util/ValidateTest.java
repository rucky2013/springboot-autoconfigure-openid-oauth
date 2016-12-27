package com.alexbt.appdirect.notifications.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ValidateTest {

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyString() {
        Validate.notEmpty("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullString() {
        Validate.notEmpty((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArray() {
        Validate.notEmpty((Object[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyArray() {
        Validate.notEmpty(new Object[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullMap() {
        Validate.notEmpty((Map<?, ?>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMap() {
        Validate.notEmpty(new HashMap<Object, Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullObject() {
        String str = null;
        Validate.notNull(str);
    }

    @Test
    public void testNotNullObject() {
        Validate.notNull(new Object());
    }

    @Test
    public void testStringOk() {
        Validate.notEmpty("ok");
    }

    @Test
    public void testArrayOk() {
        Validate.notEmpty(new Object[1]);
    }
}
