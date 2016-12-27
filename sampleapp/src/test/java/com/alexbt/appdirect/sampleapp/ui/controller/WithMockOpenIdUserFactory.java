package com.alexbt.appdirect.sampleapp.ui.controller;

import static org.mockito.Mockito.mock;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;

import com.alexbt.appdirect.sampleapp.util.WebConstants;

public class WithMockOpenIdUserFactory implements WithSecurityContextFactory<WithMockOpenIdUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockOpenIdUser withUser) {
        List<OpenIDAttribute> attributes = new ArrayList<OpenIDAttribute>();

        for (String att : withUser.attributeExchange()) {
            Assert.isTrue(att.contains(":"));
            String[] split = att.split(":");
            attributes.add(new OpenIDAttribute(split[0], null, Collections.singletonList(split[1])));
        }
        Authentication authentication = new OpenIDAuthenticationToken(mock(Principal.class),
                Collections.singletonList(new SimpleGrantedAuthority(WebConstants.WEB_USER)), "identity", attributes);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

}