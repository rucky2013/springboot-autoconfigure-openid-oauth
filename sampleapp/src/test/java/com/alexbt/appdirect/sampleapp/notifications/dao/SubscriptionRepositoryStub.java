package com.alexbt.appdirect.sampleapp.notifications.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.alexbt.appdirect.notifications.model.User;
import com.alexbt.appdirect.sampleapp.notifications.model.Subscription;

public class SubscriptionRepositoryStub implements SubscriptionRepository {
    private Map<String, Subscription> subscriptionsById = new HashMap<String, Subscription>();
    private Map<String, Subscription> subscriptionsByAccountIdentifier = new HashMap<String, Subscription>();
    private Map<String, Subscription> subscriptionsByUserUuid = new HashMap<String, Subscription>();

    @Override
    public Iterable<Subscription> findAll(Sort sort) {
        throw new AssertionError();
    }

    @Override
    public Page<Subscription> findAll(Pageable pageable) {
        throw new AssertionError();
    }

    @Override
    public <S extends Subscription> S save(S entity) {
        subscriptionsById.put(entity.getId(), entity);
        subscriptionsByAccountIdentifier.put(entity.getAccount().getAccountIdentifier(), entity);
        for (User user : entity.getUsers()) {
            subscriptionsByUserUuid.put(user.getUuid(), entity);
        }

        return entity;
    }

    @Override
    public <S extends Subscription> Iterable<S> save(Iterable<S> entities) {
        throw new AssertionError();
    }

    @Override
    public Subscription findOne(String id) {
        throw new AssertionError();
    }

    @Override
    public boolean exists(String id) {
        throw new AssertionError();
    }

    @Override
    public Iterable<Subscription> findAll(Iterable<String> ids) {
        throw new AssertionError();
    }

    @Override
    public long count() {
        throw new AssertionError();
    }

    @Override
    public void delete(String id) {
        throw new AssertionError();

    }

    @Override
    public void delete(Subscription entity) {
        throw new AssertionError();

    }

    @Override
    public void delete(Iterable<? extends Subscription> entities) {
        throw new AssertionError();

    }

    @Override
    public void deleteAll() {
        subscriptionsByAccountIdentifier.clear();
        subscriptionsById.clear();
        subscriptionsByUserUuid.clear();

    }

    @Override
    public Subscription findOneByAccountAccountIdentifier(String accountIdentifier) {
        return subscriptionsByAccountIdentifier.get(accountIdentifier);
    }

    @Override
    public Subscription findOneByUsersUuid(String uuid) {
        return subscriptionsByUserUuid.get(uuid);
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionsById.values().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteByAccountAccountIdentifier(String accountIdentifier) {
        Subscription subscription = subscriptionsByAccountIdentifier.remove(accountIdentifier);
        if (subscription != null) {
            subscriptionsById.remove(subscription.getId());
            for (User user : subscription.getUsers()) {
                subscriptionsByUserUuid.remove(user.getUuid());
            }
        }

    }

}
