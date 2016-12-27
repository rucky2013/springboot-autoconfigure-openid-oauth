package com.alexbt.appdirect.sampleapp.notifications.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.alexbt.appdirect.notifications.model.Account;
import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.enu.Status;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.alexbt.appdirect.notifications.model.response.Response;
import com.alexbt.appdirect.sampleapp.notifications.service.SubscriptionService;
import com.alexbt.appdirect.sampleapp.notifications.util.NotificationValidate;
import com.alexbt.autoconfigure.oauth.controller.AbstractOAuthController;

/**
 * Abstract Controller to handle commons tasks (such as validating mandatory fields, accountIdentifier, locking...)
 * @author alexbt
 *
 */
public abstract class AbstractController extends AbstractOAuthController<Notification, Response> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationValidate.class);

    @Autowired
    protected NotificationValidate notificationValidate;

    @Autowired
    protected SubscriptionService subscriptionService;

    @Value("${alexbt.oauth.authority}")
    private String oauthAuthority;

    @Override
    public String getOAuthAuthority() {
        return oauthAuthority;
    }

    /**
     * Handles the notification request
     */
    @Override
    protected final ResponseEntity<Response> handleRequest(Notification notification) {
        Assert.notNull(notification);
        assignThreadName(notification.getType());

        notificationValidate.isNotStateless(notification.getFlag());
        notificationValidate.hasAllRequiredFields(notification);
        notificationValidate.isExpectedType(getSupportedNotificationType(), notification.getType());

        Account account = retrieveAccount(notification);

        ResponseEntity<Response> response = handleNotification(notification, account);
        LOGGER.debug("Response: {}", response);
        return response;
    }

    private void assignThreadName(Type type) {
        Thread.currentThread().setName(String.valueOf(type));
    }

    private Account retrieveAccount(Notification notification) {
        final Account account;

        if (Type.SUBSCRIPTION_ORDER.equals(notification.getType())) {
            account = new Account(String.valueOf(UUID.randomUUID()), Status.ACTIVE);
        } else {
            account = notification.getPayload().getAccount();
        }

        LOGGER.debug("Using '{}'", account);
        return account;
    }

    /**
     * Concrete method implemented by each controllers
     * @param notification the notification received from the marketplace
     * @param account the associated account
     * @return the response
     */
    protected abstract ResponseEntity<Response> handleNotification(Notification notification, Account account);

    /**
     * Returns the supported Notification type for the current Controller.
     * @return the controller's supported type
     */
    protected abstract Type getSupportedNotificationType();

}