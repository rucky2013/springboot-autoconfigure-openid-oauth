package com.alexbt.appdirect.sampleapp.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexbt.appdirect.sampleapp.notifications.dao.SubscriptionRepository;
import com.alexbt.appdirect.sampleapp.notifications.model.Subscription;
import com.alexbt.appdirect.sampleapp.util.WebConstants;

/**
 * Admin controller
 * @author alexbt
 *
 */
@RestController
@PreAuthorize(WebConstants.AUTHORITY_WEB_USER)
public class SubscriptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    /**
     * Retrieves the list of subscriptions.
     * @return the list of subscriptions.
     */
    @RequestMapping(WebConstants.WEB_URI_SUBSCRIPTIONS)
    public List<Subscription> listSubscriptions() {
        Thread.currentThread().setName("listSubscriptions");
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        LOGGER.debug("Fetching all subscription: {}", subscriptions);
        return subscriptions;
    }
}
