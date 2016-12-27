package com.alexbt.appdirect.sampleapp.notifications.exception;

import com.alexbt.appdirect.notifications.model.response.ErrorCode;

/**
 * This error code is typically used when AppDirect admins try to add or remove users from an account not found in the
 * application vendor's records.
 * @author alexbt
 *
 */
public class AccountNotFoundException extends NotificationException {

    private static final long serialVersionUID = 3999304631196627697L;

    /**
     * Constructor.
     * @param message the message
     */
    public AccountNotFoundException(String message) {
        super(ErrorCode.ACCOUNT_NOT_FOUND, message);
    }
}
