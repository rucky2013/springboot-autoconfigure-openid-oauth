package com.alexbt.autoconfigure.openid.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * UserDetailsService implementation.
 * @author alexbt
 *
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Value("${alexbt.openid.authority}")
    private String openidAuthority;

    private static final String NO_PASSWORD = "";

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken openIdToken) {
        LOGGER.info("Loading openIdToken.getCredentials: '{}'", openIdToken.getCredentials());
        LOGGER.info("Loading openIdToken.getDetails: '{}'", openIdToken.getDetails());
        LOGGER.info("Loading openIdToken.getPrincipal: '{}'", openIdToken.getPrincipal());
        LOGGER.info("Loading openIdToken.getAttributes: '{}'", openIdToken.getAttributes());
        LOGGER.info("Loading openIdToken.getIdentityUrl: '{}'", openIdToken.getIdentityUrl());
        LOGGER.debug("Loading UserDetails for '{}'", openIdToken.getPrincipal());
        return loadUserByUsername(String.valueOf(openIdToken.getPrincipal()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LOGGER.debug("Loading User by username '{}'", username);
        return new User(username, NO_PASSWORD, AuthorityUtils.createAuthorityList(openidAuthority));
    }
}