package com.alexbt.autoconfigure.openid.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OpenId Attributes loaded from properties
 * @author alexbt
 *
 */
@Component
@ConfigurationProperties("alexbt.openid.attributeExchange")
public class OpenIdAttributeExchange {

    private String pattern;

    private Map<String, String> properties;

    /**
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the fields
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * @param fields the fields to set
     */
    public void setProperties(Map<String, String> fields) {
        this.properties = fields;
    }

}
