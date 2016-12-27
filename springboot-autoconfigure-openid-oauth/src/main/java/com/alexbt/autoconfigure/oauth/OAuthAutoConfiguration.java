package com.alexbt.autoconfigure.oauth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Conditional spring boot autoconfiguration for OAuth.
 * @author alexbt
 *
 */
@Configuration
@ComponentScan(excludeFilters = @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class))
@ConditionalOnProperty(matchIfMissing = true, name = "alexbt.oauth.enabled", havingValue = "true")
public class OAuthAutoConfiguration {
}