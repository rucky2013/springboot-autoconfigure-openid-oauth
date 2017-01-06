package com.alexbt.autoconfigure.openid.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import com.alexbt.autoconfigure.openid.properties.OpenIdProvider;
import com.alexbt.autoconfigure.openid.properties.OpenIdProviderList;

/**
 * LogoutSuccessHandler implementation.
 * @author alexbt
 *
 */
@Component
public class OpenIdLogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenIdLogoutSuccessHandlerImpl.class);
    private static final String FORWARD_SLASH = "/";

    private OpenIdProviderList openIdProviderList;
    private HttpClient httpClient;

    /**
     * Constructor.
     * @param openIdProviderList injected list of OpenId providers
     * @param httpClient the httpClient
     */
    @Autowired
    public OpenIdLogoutSuccessHandlerImpl(OpenIdProviderList openIdProviderList, HttpClient httpClient) {
        this.openIdProviderList = openIdProviderList;
        this.httpClient = httpClient;
        LOGGER.debug("Injecting {}", openIdProviderList);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        LOGGER.info("Performing logout");
        redirectToHome(response);
        LOGGER.debug("Logging out principal '{}'", authentication);

        try {
            if (authentication == null) {
                LOGGER.debug("No user is currently logged in");
            } else if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof User) {
                LOGGER.debug("Logging out principal '{}'", authentication.getPrincipal());
                final String openIdUrl = ((User) authentication.getPrincipal()).getUsername();
                LOGGER.debug("Logging out '{}'", openIdUrl);

                sendLogoutToProvider(request.getRequestURI(), openIdUrl);
            }
        } finally {
            // In case an error occurs, logout user from session
            new SecurityContextLogoutHandler().logout(request, response, null);
        }
    }

    private void redirectToHome(HttpServletResponse response) throws IOException {
        LOGGER.info("Redirecting to '{}'", FORWARD_SLASH);
        response.sendRedirect(FORWARD_SLASH);
    }

    private void sendLogoutToProvider(String logoutUri, String openId) throws IOException, ClientProtocolException {
        LOGGER.info("logoutUri: '{}'", logoutUri);
        if (StringUtils.startsWith(logoutUri, OpenIdSecurityConfig.LOGOUT_URI)) {
            String providerLogout = retrieveOpenIdLogoutUrl(logoutUri);
            LOGGER.debug("Calling external logout uri: '{}'", providerLogout);

            providerLogout = providerLogout.replace("{alexbt.openId.placeholder}", openId);
            LOGGER.debug("Openid logout final uri: '{}'", providerLogout);

            HttpPost httpPost = new HttpPost(providerLogout);
            httpClient.execute(httpPost);
            LOGGER.info("Continue after logout");
        }
    }

    private String retrieveOpenIdLogoutUrl(String logoutUri) {
        String providerName = StringUtils.removeStart(logoutUri, OpenIdSecurityConfig.LOGOUT_URI);
        providerName = StringUtils.removeStart(providerName, FORWARD_SLASH);
        OpenIdProvider openIdProvider = openIdProviderList.get(providerName);
        if (openIdProvider == null) {
            throw new MissingRequiredPropertiesException();
        }
        return openIdProvider.getLogout();
    }
}