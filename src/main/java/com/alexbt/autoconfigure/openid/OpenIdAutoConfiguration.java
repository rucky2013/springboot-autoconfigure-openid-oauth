package com.alexbt.autoconfigure.openid;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Conditional spring boot autoconfiguration for OpenId.
 * @author alexbt
 *
 */
@Configuration
@ComponentScan(excludeFilters = @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class))
@ConditionalOnProperty(matchIfMissing = true, name = "alexbt.openid.enabled", havingValue = "true")
public class OpenIdAutoConfiguration {

    /**
     * HttpClient bean
     * @return the HttpClient bean
     */
    @Bean
    public HttpClient httpClient() {
        return HttpClientBuilder.create().build();
    }
}
