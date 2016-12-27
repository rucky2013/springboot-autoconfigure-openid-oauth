package com.alexbt.appdirect.notifications.builder;

import com.alexbt.appdirect.notifications.model.Address;
import com.alexbt.appdirect.notifications.util.Validate;

public class AddressBuilder {

    private Address address = new Address();

    private AddressBuilder() {
    }

    public static AddressBuilder builder() {
        return new AddressBuilder();
    }

    public Address get() {
        return address;
    }

    public AddressBuilder city(String city) {
        Validate.notEmpty(city);
        address.setCity(city);
        return this;
    }

    public AddressBuilder country(String country) {
        Validate.notEmpty(country);
        address.setCountry(country);
        return this;
    }

    public AddressBuilder state(String state) {
        Validate.notEmpty(state);
        address.setState(state);
        return this;
    }

    public AddressBuilder street1(String street1) {
        Validate.notEmpty(street1);
        address.setStreet1(street1);
        return this;
    }

    public AddressBuilder street2(String street2) {
        address.setStreet2(street2);
        return this;
    }

    public AddressBuilder zip(String zip) {
        address.setZip(zip);
        return this;
    }

}
