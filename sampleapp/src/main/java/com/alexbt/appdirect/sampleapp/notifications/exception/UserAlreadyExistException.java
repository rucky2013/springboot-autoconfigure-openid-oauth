package com.alexbt.appdirect.sampleapp.notifications.exception;

import com.alexbt.appdirect.notifications.model.response.ErrorCode;

/**
 * This error code is typically used when AppDirect admins try to buy subscriptions for apps they have already purchased
 * directly from the application vendor. In this scenario, we will show the user an error message and prompt them to
 * link their account.
 * @author alexbt
 *
 */
public class UserAlreadyExistException extends NotificationException {

    private static final long serialVersionUID = -5860673070542107504L;

    /**
     * Constructor.
     * @param message the message
     */
    public UserAlreadyExistException(String message) {
        super(ErrorCode.USER_ALREADY_EXISTS, message);
    }
}
