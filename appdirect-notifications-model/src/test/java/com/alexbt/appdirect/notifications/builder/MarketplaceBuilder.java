package com.alexbt.appdirect.notifications.builder;

import com.alexbt.appdirect.notifications.model.Marketplace;
import com.alexbt.appdirect.notifications.util.Validate;

public class MarketplaceBuilder {

    private Marketplace marketplace = new Marketplace();

    public static MarketplaceBuilder builder() {
        return new MarketplaceBuilder();
    }

    public Marketplace get() {
        return marketplace;
    }

    public MarketplaceBuilder partner(String partner) {
        Validate.notEmpty(partner);
        marketplace.setPartner(partner);
        return this;
    }

    public MarketplaceBuilder baseUrl(String baseUrl) {
        Validate.notEmpty(baseUrl);
        marketplace.setBaseUrl(baseUrl);
        return this;
    }

}
