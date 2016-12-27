package com.alexbt.appdirect.notifications.builder;

import com.alexbt.appdirect.notifications.model.AddonInstance;
import com.alexbt.appdirect.notifications.model.Payload;
import com.alexbt.appdirect.notifications.util.Validate;

public class PayloadBuilder extends Payload {
    private Payload payload = new Payload();

    private UserBuilder personBuilder = UserBuilder.builder();
    private NoticeBuilder noticeBuilder = NoticeBuilder.builder();
    private OrderBuilder orderBuilder = OrderBuilder.builder();
    private CompanyBuilder companyBuilder = CompanyBuilder.builder();
    private AccountBuilder accountBuilder = AccountBuilder.builder();

    private PayloadBuilder() {
    }

    public UserBuilder getUserBuilder() {
        return personBuilder;
    }

    public static PayloadBuilder builder() {
        return new PayloadBuilder();
    }

    public NoticeBuilder getNoticeBuilder() {
        return noticeBuilder;
    }

    public OrderBuilder getOrderBuilder() {
        return orderBuilder;
    }

    public CompanyBuilder getCompanyBuilder() {
        return companyBuilder;
    }

    public AccountBuilder getAccountBuilder() {
        return accountBuilder;
    }

    public Payload get() {
        payload.setAccount(accountBuilder.get());
        payload.setNotice(noticeBuilder.get());
        payload.setOrder(orderBuilder.get());
        payload.setCompany(companyBuilder.get());
        payload.setUser(personBuilder.get());
        return payload;
    }

    public PayloadBuilder addonInstanceId(String addonInstanceId) {
        Validate.notEmpty(addonInstanceId);
        AddonInstance addonInstance = new AddonInstance();
        addonInstance.setId(addonInstanceId);
        payload.setAddonInstance(addonInstance);
        return this;
    }
}
