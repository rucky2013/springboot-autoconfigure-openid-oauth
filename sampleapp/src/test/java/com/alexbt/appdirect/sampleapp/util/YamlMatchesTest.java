package com.alexbt.appdirect.sampleapp.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.junit4.SpringRunner;

import com.alexbt.appdirect.sampleapp.notifications.dao.SubscriptionRepository;
import com.alexbt.appdirect.sampleapp.notifications.service.SubscriptionService;
import com.alexbt.appdirect.sampleapp.notifications.util.NotificationValidate;
import com.alexbt.autoconfigure.oauth.controller.TwoLeggedControllerHelper;

@RunWith(SpringRunner.class)
@WebMvcTest
@MockBeans({ @MockBean(TwoLeggedControllerHelper.class), @MockBean(NotificationValidate.class), //
        @MockBean(SubscriptionService.class), @MockBean(SubscriptionRepository.class) })
public class YamlMatchesTest {

    @Value("${alexbt.oauth.authority}")
    private String oauthAuthority;

    @Value("${alexbt.openid.authority}")
    private String openidAuthority;

    @Value("${alexbt.oauth.secured-uri-pattern}")
    private String oauthSecuredUriPattern;

    @Value("${alexbt.openid.secured-uri-pattern}")
    private String openIdSecuredUriPattern;

    @Test
    public void yamlOAuthAuthorityConfigMatches() {
        assertEquals(WebConstants.MARKETPLACE_USER, oauthAuthority);
    }

    @Test
    public void yamlOpenIdAuthorityConfigMatches() {
        assertEquals(WebConstants.WEB_USER, openidAuthority);
    }

    @Test
    public void yamlOAuthBaseUriMatches() {
        assertEquals(WebConstants.INTG_BASE_URI + "/**", oauthSecuredUriPattern);
    }

    @Test
    public void yamlOpenIdBaseUriMatches() {
        assertEquals(WebConstants.WEB_BASE_URI + "/**", openIdSecuredUriPattern);
    }
}
