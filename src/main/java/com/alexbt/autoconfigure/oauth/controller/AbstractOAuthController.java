package com.alexbt.autoconfigure.oauth.controller;

import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Abstrdact controller to be implemented by Controllers requiring 2-Legged OAuth
 * @author alexbt
 *
 * @param <T> the expected returned object type
 * @param <R> the response type
 */
public abstract class AbstractOAuthController<T, R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractOAuthController.class);

    @Autowired
    private TwoLeggedControllerHelper twoLeggedControllerHelper;

    @Value("${alexbt.oauth.authority}")
    private String oAuthAuthority;

    private Class<T> type;

    /**
     * Constructor.
     * @param genericType the implicit generic type
     */
    @SuppressWarnings("unchecked")
    public AbstractOAuthController(T... genericType) {
        type = (Class<T>) genericType.getClass().getComponentType();
    }

    /**
     * Single method exposed for all implementing controller
     * @param url the callback oauth url
     * @return the final success/failure response
     * @throws URISyntaxException thrown if the uri is of bad format
     */
    @RequestMapping
    @PreAuthorize("hasAuthority(#root.this.oAuthAuthority)")
    public ResponseEntity<R> twoLeggedExchange(@RequestParam("${alexbt.oauth.urlParam}") URL url) throws URISyntaxException {
        LOGGER.debug("Received request for {} with two legged url to call: {}", getClass().getSimpleName(), url);
        T body = twoLeggedControllerHelper.twoLeggedExchange(url, type);

        return handleRequest(body);
    }

    protected abstract ResponseEntity<R> handleRequest(T requestBody);

    /**
     * Retrieves the required oauth authority
     * @return the required oauth authority
     */
    public String getOAuthAuthority() {
        return oAuthAuthority;
    }
}
