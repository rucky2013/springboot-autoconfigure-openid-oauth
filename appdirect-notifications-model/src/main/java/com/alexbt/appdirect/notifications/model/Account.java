package com.alexbt.appdirect.notifications.model;

import javax.validation.constraints.NotNull;

import com.alexbt.appdirect.notifications.model.enu.Status;

/**
 * Account details.
 * @author alexbt
 *
 */
public class Account {

    @NotNull
    private String accountIdentifier;

    @NotNull
    private Status status;

    /**
     * Constructor used when creating a new Account
     * @param accountIdentifier unique account identifier
     * @param status account status
     */
    public Account(String accountIdentifier, Status status) {
        this.accountIdentifier = accountIdentifier;
        this.status = status;
    }

    /**
     * Constructor for Serialization/Deserialization
     */
    public Account() {
    }

    /**
     * @return the accountIdentifier
     */
    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    /**
     * @param accountIdentifier the accountIdentifier to set
     */
    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account [accountIdentifier=" + accountIdentifier + ", status=" + status + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountIdentifier == null) ? 0 : accountIdentifier.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Account other = (Account) obj;
        if (accountIdentifier == null) {
            if (other.accountIdentifier != null) {
                return false;
            }
        } else if (!accountIdentifier.equals(other.accountIdentifier)) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        return true;
    }
}
