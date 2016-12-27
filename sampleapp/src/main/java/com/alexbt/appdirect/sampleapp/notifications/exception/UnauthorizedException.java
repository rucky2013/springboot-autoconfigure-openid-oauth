package com.alexbt.appdirect.sampleapp.notifications.exception;

import com.alexbt.appdirect.notifications.model.response.ErrorCode;

/**
 * This error code is returned when users try any action that is not authorized for that particular application, for
 * example, if an application does not allow the original creator to be unassigned.
 * @author alexbt
 *
 */
public class UnauthorizedException extends NotificationException {

    private static final long serialVersionUID = -5860673070542107502L;

    /**
     * Constructor.
     * @param message the message
     */
    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }
}
