package com.alexbt.appdirect.sampleapp.notifications.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.alexbt.appdirect.notifications.model.Account;
import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.Payload;
import com.alexbt.appdirect.notifications.model.User;
import com.alexbt.appdirect.notifications.model.enu.NoticeType;
import com.alexbt.appdirect.sampleapp.notifications.dao.SubscriptionRepository;
import com.alexbt.appdirect.sampleapp.notifications.model.Subscription;
import com.alexbt.appdirect.sampleapp.util.WebConstants;

/**
 * Service handling subscription operations.
 * @author alexbt
 *
 */
@Service
@PreAuthorize(WebConstants.AUTHORITY_MARKETPLACE)
public class SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);

    private SubscriptionRepository repository;

    /**
     * Constructor
     * @param repository the SubscriptionRepository
     */
    @Autowired
    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    /**
     * Create operation
     * @param notification the notification
     * @param account the account
     */
    public void create(Notification notification, Account account) {
        Subscription subscription = new Subscription();
        Payload payload = notification.getPayload();

        subscription.setAccount(account);
        subscription.setCompany(payload.getCompany());
        subscription.setOrder(payload.getOrder());

        User creator = notification.getCreator();
        subscription.setCreatorUuid(creator.getUuid());
        subscription.addUser(creator);
        subscription.addNotification(notification);

        LOGGER.debug("Saving new subscription: {}", subscription);
        repository.save(subscription);
    }

    /**
     * Update operation
     * @param notification the notification
     * @param account the account
     */
    public void update(Notification notification, Account account) {
        Subscription subscription = repository.findOneByAccountAccountIdentifier(account.getAccountIdentifier());
        LOGGER.debug("Found current subscription: {}", subscription);

        Payload payload = notification.getPayload();
        subscription.setOrder(payload.getOrder());
        subscription.setAccount(payload.getAccount());
        subscription.addNotification(notification);

        LOGGER.debug("Saving subscription: {}", subscription);
        repository.save(subscription);
    }

    /**
     * Add User operation
     * @param notification the notification
     * @param account the account
     */
    public void addUser(Notification notification, Account account) {
        Subscription subscription = repository.findOneByAccountAccountIdentifier(account.getAccountIdentifier());
        LOGGER.debug("Found current subscription: {}", subscription);

        subscription.addUser(notification.getPayload().getUser());
        subscription.addNotification(notification);

        LOGGER.debug("Saving subscription: {}", subscription);
        repository.save(subscription);
    }

    /**
     * Remove User operation
     * @param notification the notification
     * @param account the account
     */
    public void removeUser(Notification notification, Account account) {
        Subscription subscription = repository.findOneByAccountAccountIdentifier(account.getAccountIdentifier());
        LOGGER.debug("Found current subscription: {}", subscription);

        subscription.removeUser(notification.getPayload().getUser());
        subscription.addNotification(notification);

        LOGGER.debug("Saving subscription: {}", subscription);
        repository.save(subscription);
    }

    /**
     * Cancel operation
     * @param notification the notification
     * @param account the account
     */
    public void cancel(Notification notification, Account account) {
        LOGGER.debug("Removing subscription with account id '{}'", account.getAccountIdentifier());
        repository.deleteByAccountAccountIdentifier(account.getAccountIdentifier());
    }

    /**
     * Status operation
     * @param notification the notification
     * @param account the account
     */
    public void notice(Notification notification, Account account) {
        if (NoticeType.CLOSED.equals(notification.getPayload().getNotice().getType())) {
            LOGGER.debug("Performing 'cancel' for Notice 'Closed'");
            cancel(notification, account);

        } else {
            Subscription subscription = repository.findOneByAccountAccountIdentifier(account.getAccountIdentifier());
            LOGGER.debug("Found current subscription: {}", subscription);

            Payload payload = notification.getPayload();
            subscription.setAccount(payload.getAccount());
            subscription.setNotice(payload.getNotice());
            subscription.addNotification(notification);

            LOGGER.debug("Saving subscription: {}", subscription);
            repository.save(subscription);
        }
    }
}