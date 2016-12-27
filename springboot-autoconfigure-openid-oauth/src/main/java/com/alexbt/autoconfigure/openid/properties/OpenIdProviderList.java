package com.alexbt.autoconfigure.openid.properties;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * List of OpenId Providers loaded from properties
 * @author alexbt
 *
 */
@Component
@ConfigurationProperties("alexbt.openid")
public class OpenIdProviderList {
    private static final String ONE_OR_SEVERAL_PROVIDERS_MUST_BE_DEFINED_MSG = "One or several providers must be define using openid.providers";

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenIdProviderList.class);

    private Map<String, OpenIdProvider> providers;

    public Map<String, OpenIdProvider> getProviders() {
        return providers;
    }

    public void setProviders(Map<String, OpenIdProvider> providers) {
        this.providers = providers;
    }

    /**
     * Retrieve the OpenId Provider corresponding to a given name
     * @param name the provider name
     * @return the OpenId Provider corresponding to a given name
     */
    public OpenIdProvider get(String name) {
        Assert.notNull(name);
        Assert.notEmpty(providers, ONE_OR_SEVERAL_PROVIDERS_MUST_BE_DEFINED_MSG);
        OpenIdProvider openIdProvider = providers.get(name);
        Assert.notNull(openIdProvider, "A provider named '" + name + "' must be defined using openid.providers." + name);
        return providers.get(name);
    }

    /**
     * Logs the provider after its loaded.
     */
    @PostConstruct
    public void postConstruct() {
        LOGGER.debug("Loaded {}", this);
    }

    @Override
    public String toString() {
        return "OpenIdProviders [providers=" + providers + "]";
    }

}
