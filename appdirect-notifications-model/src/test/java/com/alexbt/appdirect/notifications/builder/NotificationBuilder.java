package com.alexbt.appdirect.notifications.builder;

import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.enu.Flag;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.alexbt.appdirect.notifications.util.Validate;

public class NotificationBuilder {

    private Notification notification = new Notification();
    private UserBuilder userBuilder = UserBuilder.builder();
    private PayloadBuilder payloadBuilder = PayloadBuilder.builder();
    private MarketplaceBuilder marketplaceBuilder = MarketplaceBuilder.builder();

    private NotificationBuilder() {
    }

    public static NotificationBuilder getBuilder() {
        return new NotificationBuilder();
    }

    public Notification get() {
        notification.setCreator(userBuilder.get());
        notification.setPayload(payloadBuilder.get());
        notification.setMarketplace(marketplaceBuilder.get());
        return notification;
    }

    public UserBuilder getUserBuilder() {
        return userBuilder;
    }

    public PayloadBuilder getPayloadBuilder() {
        return payloadBuilder;
    }

    public MarketplaceBuilder getMarketPlaceBuilder() {
        return marketplaceBuilder;
    }

    public NotificationBuilder type(Type type) {
        Validate.notNull(type);
        notification.setType(type);
        return this;
    }

    public NotificationBuilder applicationUuid(String applicationUuid) {
        Validate.notEmpty(applicationUuid);
        notification.setApplicationUuid(applicationUuid);
        return this;
    }

    public NotificationBuilder flag(Flag flag) {
        Validate.notNull(flag);
        notification.setFlag(flag);
        return this;
    }

    public NotificationBuilder returnUrl(String returnUrl) {
        Validate.notEmpty(returnUrl);
        notification.setReturnUrl(returnUrl);
        return this;
    }
}
