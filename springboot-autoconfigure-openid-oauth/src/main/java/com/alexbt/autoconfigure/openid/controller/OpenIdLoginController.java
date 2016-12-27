package com.alexbt.autoconfigure.openid.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alexbt.autoconfigure.openid.config.OpenIdSecurityConfig;
import com.alexbt.autoconfigure.openid.properties.OpenIdProvider;
import com.alexbt.autoconfigure.openid.properties.OpenIdProviderList;

/**
 * Web controller handling login request, using the OpenId configuration (alexbt.openid.*)
 * @author alexbt
 *
 */
@Controller
public class OpenIdLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenIdLoginController.class);
    private static final String PROVIDER_NAME = "providerName";
    public static final String OPENID_IDENTIFIER = "?openid_identifier=";

    private OpenIdProviderList openIdProviderList;

    /**
     * Constructor.
     * @param openIdProviderList injected openId providers
     */
    @Autowired
    public OpenIdLoginController(OpenIdProviderList openIdProviderList) {
        this.openIdProviderList = openIdProviderList;
    }

    /**
     * Handles openid login requests
     * @param request the HttpRequest
     * @param response the HttpResponse
     * @param providerName the OpenId Providername (matching the provider definition (alexbt.openid.providers.*)
     * @throws IOException if an error occurs redirecting to the openid provider
     */
    @RequestMapping(value = OpenIdSecurityConfig.EXPOSED_LOGIN_URI + "/{" + PROVIDER_NAME + "}")
    public void login(HttpServletRequest request, HttpServletResponse response, @PathVariable(PROVIDER_NAME) String providerName) throws IOException {
        Thread.currentThread().setName("login");
        Assert.notNull(request);
        Assert.notNull(response);
        Assert.notNull(providerName);

        LOGGER.debug("Performing login for provider '{}'", providerName);
        OpenIdProvider openIdprovider = openIdProviderList.get(providerName);
        if (openIdprovider == null) {
            throw new MissingRequiredPropertiesException();
        }
        String loginUrl = OpenIdSecurityConfig.INTERNAL_LOGIN_URI + OPENID_IDENTIFIER + openIdprovider.getLogin();

        login(response, openIdprovider, loginUrl);
    }

    private void login(HttpServletResponse response, OpenIdProvider openIdprovider, String loginUrl) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Using provider '{}'", openIdprovider);
            LOGGER.debug("Redirecting to login url '{}'", loginUrl);
        }
        response.sendRedirect(loginUrl);
    }
}
