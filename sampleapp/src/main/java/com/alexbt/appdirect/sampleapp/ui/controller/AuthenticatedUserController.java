package com.alexbt.appdirect.sampleapp.ui.controller;

import java.util.List;
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
    private static final String COMPANY_UUID = "companyUuid";
    private static final String COMPANY_NAME = "companyName";
    private static final String UUID = "uuid";
    private static final String EMAIL = "email";
    private static final String FULL_NAME = "fullName";
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
            Map<String, String> attributes = openIdAttributesListToMap(openIDAuthenticationToken.getAttributes());

            AuthenticatedUser authenticatedUser = new AuthenticatedUser();
            authenticatedUser.setName(attributes.get(FULL_NAME));
            authenticatedUser.setEmail(attributes.get(EMAIL));
            authenticatedUser.setUuid(attributes.get(UUID));
            authenticatedUser.setCompanyName(attributes.get(COMPANY_NAME));
            authenticatedUser.setCompanyUuid(attributes.get(COMPANY_UUID));

            LOGGER.debug("Returning current user: '{}'", authenticatedUser);
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        }

        LOGGER.info("No user logged in");
        // We should never be here since the current service can only be called by an authenticated user
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private Map<String, String> openIdAttributesListToMap(List<OpenIDAttribute> attributes) {
        return attributes.stream().collect(Collectors.toMap( //
                OpenIDAttribute::getName, //
                o -> (o.getValues() != null && !o.getValues().isEmpty()) ? o.getValues().get(0) : null));
    }
}