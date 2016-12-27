package com.alexbt.appdirect.sampleapp.notifications.exception;

import com.alexbt.appdirect.notifications.model.response.ErrorCode;

/**
 *
 * This error code is typically used when AppDirect admins try to unassign users not found in the application vendor's
 * account.
 * @author alexbt
 *
 */
public class UserNotFoundException extends NotificationException {

    private static final long serialVersionUID = -5860673070542107503L;

    /**
     * Constructor.
     * @param message the message
     */
    public UserNotFoundException(String message) {
        super(ErrorCode.USER_NOT_FOUND, message);
    }
}
