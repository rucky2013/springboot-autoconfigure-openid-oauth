package com.alexbt.autoconfigure.oauth.config;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.consumer.BaseProtectedResourceDetails;
import org.springframework.security.oauth.consumer.ProtectedResourceDetails;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.InMemoryConsumerDetailsService;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.openid.OpenIDAuthenticationFilter;

import com.alexbt.autoconfigure.openid.config.OpenIdSecurityConfig;

/**
 * OAuth Spring Configuration.
 * @author alexbt
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(OAuthSecurityConfig.OAUTH_CONFIG_ORDER)
public class OAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthSecurityConfig.class);

    static final int OAUTH_CONFIG_ORDER = OpenIdSecurityConfig.OPENID_CONFIG_ORDER + 1;

    @Value("${alexbt.oauth.key}")
    private String oauthKey;

    @Value("${alexbt.oauth.secret}")
    private String oauthSecret;

    @Value("${alexbt.oauth.authority}")
    private String oauthAuthority;

    @Value("${alexbt.oauth.secured-uri-pattern}")
    private String securedUriPattern;

    @Autowired
    private ProtectedResourceDetails protectedResourceDetails;

    @Autowired
    @Lazy
    private OAuthProtectedResourceProcessingFilter oAuthProviderProcessingFilter;

    /**
     * oauthRestTemplate bean
     * @return the oauthRestTemplate bean
     */
    @Bean
    public OAuthRestTemplate oauthRestTemplate() {
        return new OAuthRestTemplate(protectedResourceDetails);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.info("Configuring HttpSecurity");
        super.configure(http);

        http.exceptionHandling().authenticationEntryPoint(new Http401AuthenticationEntryPoint("Session realm=\"SESSION\"")) //
                .and().authorizeRequests().antMatchers(securedUriPattern).hasAuthority(oauthAuthority);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        oAuthProviderProcessingFilter.setTokenServices(oauthProviderTokenServices());
        oAuthProviderProcessingFilter.setConsumerDetailsService(consumerDetailsService());
        http.addFilterBefore(oAuthProviderProcessingFilter, OpenIDAuthenticationFilter.class);
    }

    /**
     * Declares a consumerDetailsService bean.
     * @return consumerDetailsService
     */
    @Bean
    public ConsumerDetailsService consumerDetailsService() {
        LOGGER.info("Creating ConsumerDetailsService with key '{}' and secret ${alexbt.oauth.secret}", oauthKey);
        final BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
        consumerDetails.setConsumerKey(oauthKey);
        consumerDetails.setAuthorities(AuthorityUtils.createAuthorityList(oauthAuthority));
        LOGGER.debug("Oauth consumerDetails authorities: {}", consumerDetails.getAuthorities());
        consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(oauthSecret));
        consumerDetails.setRequiredToObtainAuthenticatedToken(false);

        final InMemoryConsumerDetailsService consumerDetailsService = new InMemoryConsumerDetailsService();
        consumerDetailsService.setConsumerDetailsStore(Collections.singletonMap(oauthKey, consumerDetails));
        return consumerDetailsService;
    }

    /**
     * Declares OAuthProviderTokenServices bean.
     * @return an in-memory OAuth provider token.
     */
    @Bean
    public OAuthProviderTokenServices oauthProviderTokenServices() {
        LOGGER.info("Creating OAuthProviderTokenServices");
        return new InMemoryProviderTokenServices();
    }

    /**
     * Declares the protectedResourceDetails bean (with key/secret infos).
     * @return the protectedResourceDetails.
     */
    @Bean
    public ProtectedResourceDetails protectedResourceDetails() {
        LOGGER.info("Creating ProtectedResourceDetails");

        final BaseProtectedResourceDetails resource = new BaseProtectedResourceDetails();
        resource.setConsumerKey(oauthKey);
        resource.setSharedSecret(new SharedConsumerSecretImpl(oauthSecret));
        return resource;
    }
}