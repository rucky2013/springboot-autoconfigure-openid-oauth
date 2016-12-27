package com.alexbt.appdirect.sampleapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.alexbt.appdirect.notifications.jsr303.BeanValidatorUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Spring Configuration
 * @author alexbt
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SpringConfiguration extends RepositoryRestMvcConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringConfiguration.class);

    @Override
    protected void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        LOGGER.info("Creating ObjectMapper");
        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }

    /**
     * Filter to log requests/responses
     * @return the CommonsRequestLoggingFilter
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        LOGGER.info("Creating CommonsRequestLoggingFilter");
        CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
        crlf.setIncludeClientInfo(true);
        crlf.setIncludeQueryString(true);
        crlf.setIncludePayload(true);
        return crlf;
    }

    /**
     * ValidationUtil bean
     * @return ValidationUtil
     */
    @Bean
    public BeanValidatorUtil beanValidatorUtil() {
        LOGGER.info("Creating validationUtil");
        return BeanValidatorUtil.getInstance();
    }
}
