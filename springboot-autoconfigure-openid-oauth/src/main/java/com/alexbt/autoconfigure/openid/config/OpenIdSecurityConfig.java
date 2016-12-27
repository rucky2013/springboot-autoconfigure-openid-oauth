package com.alexbt.autoconfigure.openid.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.openid.OpenIDLoginConfigurer;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.alexbt.autoconfigure.openid.properties.OpenIdAttributeExchange;

/**
 * OpenId Spring configuration.
 * @author alexbt
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(OpenIdSecurityConfig.OPENID_CONFIG_ORDER)
@ConfigurationProperties(prefix = "alexbt.openid")
public class OpenIdSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final int OPENID_CONFIG_ORDER = 95;

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenIdSecurityConfig.class);
    static final String LOGOUT_URI = "/logout/openid";
    public static final String INTERNAL_LOGIN_URI = "/login/openid/internal";
    public static final String EXPOSED_LOGIN_URI = "/login/openid";

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationUserDetailsService<OpenIDAuthenticationToken> authenticationUserDetailsService;

    @Autowired
    private OpenIdAttributeExchange openIdAttributeExchange;

    @Value("${alexbt.openid.secured-uri-pattern}")
    private String securedUriPattern;

    @Value("${alexbt.openid.authority}")
    private String openIdAuthority;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.debug("Exposing logout url: {}", LOGOUT_URI);
        LOGGER.debug("Exposing login url: {}", EXPOSED_LOGIN_URI);
        LOGGER.debug("Internal login url: {}", INTERNAL_LOGIN_URI);

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URI + "/**")).logoutSuccessHandler(logoutSuccessHandler);
        http.csrf().disable() //
                .exceptionHandling().authenticationEntryPoint(new Http401AuthenticationEntryPoint("Session realm=\"SESSION\"")) //
                .and().authorizeRequests().antMatchers(securedUriPattern).hasAuthority(openIdAuthority) //
                .and().authorizeRequests().anyRequest().permitAll();

        OpenIDLoginConfigurer<HttpSecurity> openIDLoginConfigurer = http.openidLogin().authenticationUserDetailsService(authenticationUserDetailsService);
        if (openIdAttributeExchange != null) {
            OpenIDLoginConfigurer<HttpSecurity>.AttributeExchangeConfigurer attributeExchange = openIDLoginConfigurer
                    .attributeExchange(openIdAttributeExchange.getPattern());

            Map<String, String> attributeExchangeFields = openIdAttributeExchange.getProperties();
            for (String attribute : attributeExchangeFields.keySet()) {
                attributeExchange.attribute(attribute).type(attributeExchangeFields.get(attribute)).required(true);
            }
        }

        openIDLoginConfigurer.permitAll() //
                .loginProcessingUrl(INTERNAL_LOGIN_URI) //
                .failureHandler(authenticationFailureHandler) //
                .successHandler(authenticationSuccessHandler);
    }
}