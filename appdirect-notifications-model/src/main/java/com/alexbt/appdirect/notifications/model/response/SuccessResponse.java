package com.alexbt.appdirect.notifications.model.response;

import com.alexbt.appdirect.notifications.util.Validate;

/**
 * Response to return when the event was processed successfully.
 * @author alexbt
 *
 */
public class SuccessResponse implements Response {

    private String accountIdentifier;

    /**
     * Constructor.
     * @param accountIdentifier the unique account identifier
     */
    public SuccessResponse(String accountIdentifier) {
        Validate.notEmpty(accountIdentifier);

        this.accountIdentifier = accountIdentifier;
    }

    /**
     * @return the accountIdentifier
     */
    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public String toString() {
        return "SuccessResponse [accountIdentifier=" + accountIdentifier + ", isSuccess()=" + isSuccess() + "]";
    }
}
