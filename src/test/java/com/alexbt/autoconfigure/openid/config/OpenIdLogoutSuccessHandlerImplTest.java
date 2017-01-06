package com.alexbt.autoconfigure.openid.config;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import com.alexbt.autoconfigure.openid.properties.OpenIdProvider;
import com.alexbt.autoconfigure.openid.properties.OpenIdProviderList;

@RunWith(MockitoJUnitRunner.class)
public class OpenIdLogoutSuccessHandlerImplTest {

    @InjectMocks
    private OpenIdLogoutSuccessHandlerImpl openIdLogoutSuccessHandlerImpl;

    @Mock
    private OpenIdProviderList openIdProviders;

    @Mock
    private OpenIdProvider openIdProvider;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;

    @Test
    public void testHappyPath() throws IOException {
        String LOGOUT_URI_TO_CALL = "logoutUri";
        given(authentication.getPrincipal()).willReturn(user);
        given(user.getUsername()).willReturn("openidUrl");
        given(request.getRequestURI()).willReturn("/logout/openid/providerName");

        given(openIdProviders.get("providerName")).willReturn(openIdProvider);
        given(openIdProvider.getLogout()).willReturn(LOGOUT_URI_TO_CALL);
        when(httpClient.execute(any(HttpPost.class))).then(new Answer<HttpResponse>() {

            @Override
            public HttpResponse answer(InvocationOnMock invocation) throws Throwable {
                HttpPost httpPost = invocation.getArgumentAt(0, HttpPost.class);
                Assert.assertEquals(LOGOUT_URI_TO_CALL, httpPost.getRequestLine().getUri());
                return null;
            }

        });

        openIdLogoutSuccessHandlerImpl.onLogoutSuccess(request, response, authentication);

        verify(httpClient).execute(any(HttpPost.class));
    }

    @Test
    public void testLogoutBadPrefix() throws IOException {
        given(authentication.getPrincipal()).willReturn(user);
        given(user.getUsername()).willReturn("openidUrl");
        given(request.getRequestURI()).willReturn("/wrong/uri/logout/openid/providerName");

        openIdLogoutSuccessHandlerImpl.onLogoutSuccess(request, response, authentication);

        verify(httpClient, never()).execute(any(HttpPost.class));
    }

    @Test(expected = MissingRequiredPropertiesException.class)
    public void testProviderNotConfigured() throws IOException {
        given(authentication.getPrincipal()).willReturn(user);
        given(user.getUsername()).willReturn("openidUrl");
        given(request.getRequestURI()).willReturn("/logout/openid/providerName");

        given(openIdProviders.get("providerName")).willReturn(null);

        openIdLogoutSuccessHandlerImpl.onLogoutSuccess(request, response, authentication);
    }

    @Test
    public void testAuthenticationNoUserPrincipal() throws IOException {
        given(authentication.getPrincipal()).willReturn(mock(Principal.class));

        openIdLogoutSuccessHandlerImpl.onLogoutSuccess(request, response, authentication);
        verify(httpClient, never()).execute(any(HttpPost.class));
    }

    @Test
    public void testAuthenticationNull() throws IOException {
        given(authentication.getPrincipal()).willReturn(null);

        openIdLogoutSuccessHandlerImpl.onLogoutSuccess(request, response, authentication);
        verify(httpClient, never()).execute(any(HttpPost.class));
    }

}
