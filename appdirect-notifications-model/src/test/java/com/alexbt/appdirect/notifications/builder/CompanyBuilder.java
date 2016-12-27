package com.alexbt.appdirect.notifications.builder;

import com.alexbt.appdirect.notifications.model.Company;
import com.alexbt.appdirect.notifications.util.Validate;

public class CompanyBuilder {

    private Company company = new Company();

    public static CompanyBuilder builder() {
        return new CompanyBuilder();
    }

    public Company get() {
        return company;
    }

    public CompanyBuilder uuid(String uuid) {
        Validate.notEmpty(uuid);
        company.setUuid(uuid);
        return this;
    }

    public CompanyBuilder externalId(String externalId) {
        Validate.notEmpty(externalId);
        company.setExternalId(externalId);
        return this;
    }

    public CompanyBuilder name(String name) {
        Validate.notEmpty(name);
        company.setName(name);
        return this;
    }

    public CompanyBuilder email(String email) {
        Validate.notEmpty(email);
        company.setEmail(email);
        return this;
    }

    public CompanyBuilder phoneNumber(String phoneNumber) {
        Validate.notEmpty(phoneNumber);
        company.setPhoneNumber(phoneNumber);
        return this;
    }

    public CompanyBuilder website(String website) {
        Validate.notEmpty(website);
        company.setWebsite(website);
        return this;
    }

    public CompanyBuilder country(String country) {
        Validate.notEmpty(country);
        company.setCountry(country);
        return this;
    }

}
