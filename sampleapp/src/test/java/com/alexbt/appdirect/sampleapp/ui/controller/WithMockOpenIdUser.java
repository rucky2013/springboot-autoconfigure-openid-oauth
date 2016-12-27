package com.alexbt.appdirect.sampleapp.ui.controller;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockOpenIdUserFactory.class)
public @interface WithMockOpenIdUser {
    String[] attributeExchange() default {};
}
