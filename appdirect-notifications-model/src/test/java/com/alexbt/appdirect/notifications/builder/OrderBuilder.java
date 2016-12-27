package com.alexbt.appdirect.notifications.builder;

import com.alexbt.appdirect.notifications.model.Order;
import com.alexbt.appdirect.notifications.util.Validate;

public class OrderBuilder {

    private Order order = new Order();

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public Order get() {
        return order;
    }

    public OrderBuilder editionCode(String editionCode) {
        Validate.notEmpty(editionCode);
        order.setEditionCode(editionCode);
        return this;
    }

    public OrderBuilder addonOfferingCode(String addonOfferingCode) {
        Validate.notEmpty(addonOfferingCode);
        order.setAddonOfferingCode(addonOfferingCode);
        return this;
    }

    public OrderBuilder pricingDuration(String pricingDuration) {
        Validate.notEmpty(pricingDuration);
        order.setPricingDuration(pricingDuration);
        return this;
    }

}
