package com.alexbt.autoconfigure.openid.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.MissingRequiredPropertiesException;

import com.alexbt.autoconfigure.openid.config.OpenIdSecurityConfig;
import com.alexbt.autoconfigure.openid.properties.OpenIdProvider;
import com.alexbt.autoconfigure.openid.properties.OpenIdProviderList;

@RunWith(MockitoJUnitRunner.class)
public class OpenIdLoginControllerTest {

    @InjectMocks
    private OpenIdLoginController openIdLoginController;

    @Mock
    private OpenIdProviderList openIdProviders;

    @Mock
    private OpenIdProvider openIdProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    public void testHappyPath() throws IOException {
        given(openIdProviders.get("providerName")).willReturn(openIdProvider);
        given(openIdProvider.getLogin()).willReturn("loginUri");

        openIdLoginController.login(request, response, "providerName");
        verify(response).sendRedirect(OpenIdSecurityConfig.INTERNAL_LOGIN_URI + OpenIdLoginController.OPENID_IDENTIFIER + "loginUri");
    }

    @Test(expected = MissingRequiredPropertiesException.class)
    public void testProviderNull() throws IOException {
        given(openIdProviders.get("providerName")).willReturn(null);
        openIdLoginController.login(request, response, "providerName");
    }
}
