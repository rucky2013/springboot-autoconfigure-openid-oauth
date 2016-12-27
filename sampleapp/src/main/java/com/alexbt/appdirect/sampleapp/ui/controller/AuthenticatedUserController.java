package com.alexbt.appdirect.sampleapp.ui.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexbt.appdirect.sampleapp.ui.model.AuthenticatedUser;
import com.alexbt.appdirect.sampleapp.util.WebConstants;

/**
 * Controller to retreive the current logged User
 * @author alexbt
 *
 */
@RestController
@PreAuthorize(WebConstants.AUTHORITY_WEB_USER)
public class AuthenticatedUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatedUserController.class);

    /**
     * Retrieves the currently logged in User, if any.
     * @param openIDAuthenticationToken the openIDAuthenticationToken
     * @return the user
     */
    @RequestMapping(WebConstants.WEB_URI_CURRENT_USER)
    public ResponseEntity<AuthenticatedUser> getAuthenticatedUser(OpenIDAuthenticationToken openIDAuthenticationToken) {
        Thread.currentThread().setName("getAuthenticatedUser");
        LOGGER.info("Checking current user");

        if (openIDAuthenticationToken != null) {
            Map<String, String> attributes = openIDAuthenticationToken.getAttributes().stream()
                    .collect(Collectors.toMap(OpenIDAttribute::getName, o -> o.getValues().get(0)));
            AuthenticatedUser authenticatedUser = new AuthenticatedUser();
            authenticatedUser.setName(attributes.get("fullName"));
            authenticatedUser.setEmail(attributes.get("email"));
            authenticatedUser.setUuid(attributes.get("uuid"));
            authenticatedUser.setCompanyName(attributes.get("companyName"));
            authenticatedUser.setCompanyUuid(attributes.get("companyUuid"));

            LOGGER.debug("Returning current user: '{}'", authenticatedUser);
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        }

        LOGGER.info("No user logged in");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}