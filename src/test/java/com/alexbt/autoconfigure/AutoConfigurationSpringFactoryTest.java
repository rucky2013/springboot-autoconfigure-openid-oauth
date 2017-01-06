package com.alexbt.autoconfigure;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.alexbt.autoconfigure.oauth.OAuthAutoConfiguration;
import com.alexbt.autoconfigure.openid.OpenIdAutoConfiguration;

public class AutoConfigurationSpringFactoryTest {

    @Test
    public void testAutoconfigurePackages() throws IOException {
        String stringFactories = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("META-INF/spring.factories"), Charset.forName("UTF8"));
        assertTrue(stringFactories.contains(OpenIdAutoConfiguration.class.getName()));
        assertTrue(stringFactories.contains(OAuthAutoConfiguration.class.getName()));
    }

}
