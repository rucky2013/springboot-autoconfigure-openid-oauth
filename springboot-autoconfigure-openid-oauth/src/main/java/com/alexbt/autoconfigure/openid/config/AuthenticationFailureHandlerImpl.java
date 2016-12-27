package com.alexbt.autoconfigure.openid.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Handler when the authentication fails and/or is aborted.
 * @author alexbt
 *
 */
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private static final String HOME = "/";

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        response.sendRedirect(HOME);
    }

}
