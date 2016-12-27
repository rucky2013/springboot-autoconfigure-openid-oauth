package com.alexbt.appdirect.notifications.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.junit.Before;

import com.alexbt.appdirect.notifications.builder.NotificationForTestFactory;
import com.alexbt.appdirect.notifications.jsr303.BeanValidatorUtil;

public class AbstractValidationTest {

    protected Notification notification;
    protected Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Before
    public void before() {
        notification = NotificationForTestFactory.getTestNotification();
    }

    protected Map<String, ConstraintViolation<Notification>> toMap(Set<ConstraintViolation<Notification>> violations) {
        Map<String, ConstraintViolation<Notification>> map = new HashMap<String, ConstraintViolation<Notification>>();
        for (ConstraintViolation<Notification> constraint : violations) {
            map.put(String.valueOf(constraint.getPropertyPath()), constraint);
        }
        return map;
    }

    protected void testNullField(String nullField) {
        Set<ConstraintViolation<Notification>> violations = BeanValidatorUtil.getInstance().validate(notification);

        Map<String, ConstraintViolation<Notification>> map = toMap(violations);

        assertEquals(1, violations.size());
        assertFieldValidated(NotNull.class, nullField, map);
    }

    protected void testNotificationIsValid(Notification notification) {
        Set<ConstraintViolation<Notification>> violations = BeanValidatorUtil.getInstance().validate(notification);
        assertTrue(violations.isEmpty());
    }

    protected void assertFieldValidated(Class<NotNull> constraint, String field, Map<String, ConstraintViolation<Notification>> map) {
        assertEquals("{" + constraint.getName() + ".message}", map.get(field).getMessageTemplate());
    }
}
