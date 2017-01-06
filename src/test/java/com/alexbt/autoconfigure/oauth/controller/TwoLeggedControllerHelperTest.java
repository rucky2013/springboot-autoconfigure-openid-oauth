package com.alexbt.autoconfigure.oauth.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class TwoLeggedControllerHelperTest {

    @InjectMocks
    private TwoLeggedControllerHelper twoLeggedControllerHelper;

    @Mock
    private OAuthRestTemplate oauthRestTemplate;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() throws MalformedURLException, URISyntaxException {
        Object returnedObject = new Object();
        given(oauthRestTemplate.getForObject(any(URI.class), any(Class.class))) //
                .willReturn(returnedObject);

        assertEquals(returnedObject, twoLeggedControllerHelper.twoLeggedExchange(new URL("http://dummyurl.com"), Object.class));
    }

}
