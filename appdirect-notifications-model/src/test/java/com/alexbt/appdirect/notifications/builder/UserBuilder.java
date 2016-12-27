package com.alexbt.appdirect.notifications.builder;

import java.util.Map;

import com.alexbt.appdirect.notifications.model.Address;
import com.alexbt.appdirect.notifications.model.User;
import com.alexbt.appdirect.notifications.util.Validate;

public class UserBuilder {
    private User user = new User();

    private AddressBuilder addressBuilder = AddressBuilder.builder();

    private UserBuilder() {
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public User get() {
        user.setAddress(addressBuilder.get());
        return user;
    }

    public UserBuilder email(String email) {
        Validate.notEmpty(email);
        user.setEmail(email);
        return this;
    }

    public UserBuilder firstName(String firstName) {
        Validate.notEmpty(firstName);
        user.setFirstName(firstName);
        return this;
    }

    public UserBuilder language(String language) {
        Validate.notEmpty(language);
        user.setLanguage(language);
        return this;
    }

    public UserBuilder lastName(String lastName) {
        Validate.notEmpty(lastName);
        user.setLastName(lastName);
        return this;
    }

    public UserBuilder locale(String locale) {
        Validate.notEmpty(locale);
        user.setLocale(locale);
        return this;
    }

    public UserBuilder openId(String openId) {
        Validate.notEmpty(openId);
        user.setOpenId(openId);
        return this;
    }

    public UserBuilder uuid(String uuid) {
        Validate.notEmpty(uuid);
        user.setUuid(uuid);
        return this;
    }

    public UserBuilder address(Address address) {
        Validate.notNull(address);
        user.setAddress(address);
        return this;
    }

    public UserBuilder attributes(Map<String, String> attributes) {
        Validate.notEmpty(attributes);
        user.setAttributes(attributes);
        return this;
    }

    public AddressBuilder getAddressBuilder() {
        return addressBuilder;
    }
}
