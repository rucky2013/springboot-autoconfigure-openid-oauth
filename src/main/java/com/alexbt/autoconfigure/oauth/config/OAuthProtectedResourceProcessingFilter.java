package com.alexbt.autoconfigure.oauth.config;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

/**
 * ProtectedResourceProcessingFilter implementation.
 *
 * @author alexbt
 *
 */
@Component
public class OAuthProtectedResourceProcessingFilter extends ProtectedResourceProcessingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthProtectedResourceProcessingFilter.class);

    @Value("${alexbt.oauth.secured-uri-pattern}")
    private String securedUriPattern;

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        Thread.currentThread().setName("AUTHORIZATION");
        boolean requiresAuthentication = true;

        if (new AntPathRequestMatcher(securedUriPattern).matches(request)) {
            OAuthProcessingFilterEntryPoint oauthProcessingFilterEntryPoint = new OAuthProcessingFilterEntryPoint();
            setAuthenticationEntryPoint(oauthProcessingFilterEntryPoint);
            String realmName = String.valueOf(request.getRequestURL());
            oauthProcessingFilterEntryPoint.setRealmName(realmName);
            setIgnoreMissingCredentials(false);
            LOGGER.info("Request requires authentication: '{}'", request.getRequestURL());
        } else {
            requiresAuthentication = false;
            LOGGER.info("Request does NOT require authentication '{}'", request.getRequestURL());
        }

        return requiresAuthentication;
    }
}