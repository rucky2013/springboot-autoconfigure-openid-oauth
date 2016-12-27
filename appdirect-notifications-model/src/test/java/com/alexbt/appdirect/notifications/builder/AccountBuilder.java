package com.alexbt.appdirect.notifications.builder;

import com.alexbt.appdirect.notifications.model.Account;
import com.alexbt.appdirect.notifications.model.enu.Status;
import com.alexbt.appdirect.notifications.util.Validate;

public class AccountBuilder {

    private Account account = new Account();

    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public Account get() {
        return account;
    }

    public AccountBuilder accountIdentifier(String accountIdentifier) {
        Validate.notEmpty(accountIdentifier);
        account.setAccountIdentifier(accountIdentifier);
        return this;
    }

    public AccountBuilder status(Status status) {
        Validate.notNull(status);
        account.setStatus(status);
        return this;
    }

}
