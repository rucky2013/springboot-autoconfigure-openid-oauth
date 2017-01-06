package com.alexbt.autoconfigure.oauth.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.management.Notification;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth.consumer.client.OAuthRestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class AbstractTwoLeggedControllerTest {

    private static final Object FAKE_RETURNED_PAYLOAD = new Object();

    @InjectMocks
    private AbstractOAuthController<Notification, Object> twoLeggedController = new AbstractOAuthController<Notification, Object>() {

        @Override
        protected ResponseEntity<Object> handleRequest(Notification requestBody) {
            return new ResponseEntity(FAKE_RETURNED_PAYLOAD, HttpStatus.ACCEPTED);
        }

        @Override
        public String getOAuthAuthority() {
            return "dummy";
        }
    };

    @Mock
    private TwoLeggedControllerHelper twoLeggedControllerHelper;

    @Mock
    private OAuthRestTemplate oauthRestTemplate;

    @Mock
    private Notification notification;

    @Test
    public void testHappyPath() throws URISyntaxException, MalformedURLException {
        URL url = new URL("http://blah.com/dummy");
        given(oauthRestTemplate.getForObject(any(URI.class), any(Class.class))).willReturn(notification);

        ResponseEntity<Object> response = twoLeggedController.twoLeggedExchange(url);

        assertEquals(FAKE_RETURNED_PAYLOAD, response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
}
