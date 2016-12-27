package com.alexbt.autoconfigure.oauth.controller;

import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Helper class hanlding the OAuth TwoLegged communication.
 * @author alexbt
 *
 */
@Component
public class TwoLeggedControllerHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwoLeggedControllerHelper.class);

    private OAuthRestTemplate oauthRestTemplate;

    /**
     * Constructor
     * @param oauthRestTemplate oauthRestTemplate
     */
    @Autowired
    public TwoLeggedControllerHelper(OAuthRestTemplate oauthRestTemplate) {
        this.oauthRestTemplate = oauthRestTemplate;
    }

    /**
     * Calls the url and retrieves the response
     * @param url OAuth callback url
     * @param type expected returned type
     * @param <T> expected returned type
     * @return the callback response
     * @throws URISyntaxException thrown if the url is of wrong format
     */
    public <T> T twoLeggedExchange(URL url, Class<T> type) throws URISyntaxException {
        Assert.notNull(url);
        Assert.notNull(type);
        LOGGER.debug("Calling {} for type {}", url, type);

        T body = oauthRestTemplate.getForObject(url.toURI(), type);

        LOGGER.debug("Received response {}", body);
        return body;
    }
}
