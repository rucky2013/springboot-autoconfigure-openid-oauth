package com.alexbt.appdirect.notifications.jsr303;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.alexbt.appdirect.notifications.util.Validate;

/**
 * Utility class to perform JSR303 validations
 * @author alexbt
 *
 */
public class BeanValidatorUtil {
    private static final BeanValidatorUtil SINGLETON = new BeanValidatorUtil();

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Retrieves the singleton instance.
     * @return the singleton instance.
     */
    public static BeanValidatorUtil getInstance() {
        return SINGLETON;
    }

    private BeanValidatorUtil() {
    }

    /**
     * Performs the JSR303 validation on the Notification with the provided Validator implementation
     * @param notification the notification to validate
     * @return the ConstraintViolation(s), if any error are raised. An empty set is returned otherwise.
     */
    public Set<ConstraintViolation<Notification>> validate(Notification notification) {
        Validate.notNull(notification);

        Type type = notification.getType();
        Set<ConstraintViolation<Notification>> violations = //
                type == null ? validator.validate(notification) : validator.validate(notification, type.getValidationGroup());
        violations.addAll(validator.validate(notification));
        return violations;
    }

}
