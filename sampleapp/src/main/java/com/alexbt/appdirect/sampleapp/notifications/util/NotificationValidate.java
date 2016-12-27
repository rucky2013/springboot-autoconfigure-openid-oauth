package com.alexbt.appdirect.sampleapp.notifications.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alexbt.appdirect.notifications.jsr303.BeanValidatorUtil;
import com.alexbt.appdirect.notifications.model.Account;
import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.User;
import com.alexbt.appdirect.notifications.model.enu.Flag;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.alexbt.appdirect.sampleapp.notifications.dao.SubscriptionRepository;
import com.alexbt.appdirect.sampleapp.notifications.exception.AccountNotFoundException;
import com.alexbt.appdirect.sampleapp.notifications.exception.InvalidResponseException;
import com.alexbt.appdirect.sampleapp.notifications.exception.UnauthorizedException;
import com.alexbt.appdirect.sampleapp.notifications.exception.UserAlreadyExistException;
import com.alexbt.appdirect.sampleapp.notifications.exception.UserNotFoundException;

/**
 * Utility class to validate notifications.
 * @author alexbt
 *
 */
@Component
public class NotificationValidate {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationValidate.class);
    private static final String ALREADY_EXIST = " already exist";
    private static final String RESONSE_VALIDATION_ERROR = "Response validation error(s) : ";
    private static final String COMMA = ",";

    private SubscriptionRepository repository;
    private BeanValidatorUtil beanValidatorUtil;

    /**
     * Constructor
     * @param repository the repository
     * @param beanValidatorUtil the constraintValidatorUtil
     */
    @Autowired
    public NotificationValidate(SubscriptionRepository repository, BeanValidatorUtil beanValidatorUtil) {
        this.repository = repository;
        this.beanValidatorUtil = beanValidatorUtil;
    }

    /**
     * Validates that the user does not already exist
     * @param user the user to verify
     */
    public void isNewUser(User user) {
        Assert.notNull(user);
        LOGGER.info("Validation - check user openid '{}' does not exist", user.getUuid());
        if (repository.findOneByUsersUuid(user.getUuid()) != null) {
            LOGGER.debug("User uuid '{}' already exist", user.getUuid());
            throw new UserAlreadyExistException("User " + user.getOpenId() + ALREADY_EXIST);
        }
    }

    /**
     * Validates that the user already exists
     * @param user the user to verify
     */
    public void userExists(User user) {
        Assert.notNull(user);
        LOGGER.info("Validation - check if user openid '{}' exists", user.getUuid());
        if (repository.findOneByUsersUuid(user.getUuid()) == null) {
            LOGGER.debug("User uuid '{}' does not exist", user);
            throw new UserNotFoundException("User " + user.getOpenId() + " does not exist");
        }
    }

    /**
     * Validates that the account already exists
     * @param account the account to verify
     */
    public void accountExists(Account account) {
        Assert.notNull(account);
        String accountIdentifier = account.getAccountIdentifier();
        LOGGER.info("Validating - check account identifier '{}' exists", accountIdentifier);
        if (repository.findOneByAccountAccountIdentifier(accountIdentifier) == null) {
            LOGGER.debug("Account '{}' does not exist", accountIdentifier);
            throw new AccountNotFoundException("Account " + accountIdentifier + " was not found");
        }
    }

    /**
     * Validates that the account does not already exist
     * @param account the account to verify
     */
    public void isNewAccount(Account account) {
        Assert.notNull(account);
        String accountIdentifier = account.getAccountIdentifier();
        LOGGER.info("Validating - check account identifier '{}' is new", accountIdentifier);
        if (repository.findOneByAccountAccountIdentifier(accountIdentifier) != null) {
            LOGGER.debug("Account '{}' already exists", accountIdentifier);
            throw new UnauthorizedException("Account " + accountIdentifier + " already exist!");
        }
    }

    /**
     * Validates that mandatory fields are all present (contextual to the notification type)
     * @param notification the notification to verify
     */
    public void hasAllRequiredFields(Notification notification) {
        Assert.notNull(notification);
        LOGGER.info("Performing notification's fields validations");

        Set<ConstraintViolation<Notification>> violations = beanValidatorUtil.validate(notification);
        if (violations.isEmpty()) {
            LOGGER.info("Notification validated successfully");
        } else {
            LOGGER.info("Notification validations failed");
            List<String> collect = violations.stream().map(p -> p.getPropertyPath() + " : " + p.getMessage()).collect(Collectors.toList());

            String validationMessages = StringUtils.join(collect, COMMA);
            LOGGER.info("Notification validations messages: '{}'", validationMessages);
            throw new InvalidResponseException(RESONSE_VALIDATION_ERROR + validationMessages);
        }
    }

    /**
     * Validates that the notification type is the expected type
     * @param expectedType expected notification type
     * @param type received notification type
     */
    public void isExpectedType(Type expectedType, Type type) {
        Assert.notNull(expectedType);
        Assert.notNull(type);

        if (!expectedType.equals(type)) {
            throw new InvalidResponseException("Expected notification with type " + expectedType);
        }
    }

    /**
     * Validates that the notification isn't 'Stateless'
     * @param flag the notification's flag
     * @throws UnauthorizedException if the notification is 'stateless'
     */
    public void isNotStateless(Flag flag) {
        if (Flag.STATELESS.equals(flag)) {
            LOGGER.debug("Stateless nofification -> short circuit process and return response immediately");
            throw new UnauthorizedException("Short circuit Stateless notification");
        }
    }
}