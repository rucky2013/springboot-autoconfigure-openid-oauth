package com.alexbt.appdirect.sampleapp.notifications.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.alexbt.appdirect.sampleapp.notifications.model.Subscription;
import com.alexbt.appdirect.sampleapp.util.WebConstants;

/**
 * Subscription repository
 * @author alexbt
 *
 */
@PreAuthorize(WebConstants.AUTHORITY_MARKETPLACE)
@RepositoryRestResource(exported = false)
public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, String> {

    /**
     * Retrieves the subscription matching the account identifier
     * @param accountIdentifier the unique account identifier
     * @return the matching subscription
     */
    Subscription findOneByAccountAccountIdentifier(String accountIdentifier);

    /**
     * Retrieves the subscription matching the OpenId uuid
     * @param uuid the OpenId uuid
     * @return the matching subscription
     */
    Subscription findOneByUsersUuid(String uuid);

    /**
     * Retrieves all subscription.
     * @return all subscriptions
     */
    @Override
    @PreAuthorize(WebConstants.AUTHORITY_WEB_USER)
    List<Subscription> findAll();

    /**
     * Deletes the subscription matching the account identifier
     * @param accountIdentifier the unique account identifier
     */
    void deleteByAccountAccountIdentifier(String accountIdentifier);
}
