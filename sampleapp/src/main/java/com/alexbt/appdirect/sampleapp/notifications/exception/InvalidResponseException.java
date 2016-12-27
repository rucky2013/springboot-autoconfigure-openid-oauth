package com.alexbt.appdirect.sampleapp.notifications.exception;

import com.alexbt.appdirect.notifications.model.response.ErrorCode;

/**
 * This error code is returned when the vendor was unable to process the event fetched from AppDirect.
 * @author alexbt
 *
 */
public class InvalidResponseException extends NotificationException {

    private static final long serialVersionUID = 9214521749216962278L;

    /**
     * Constructor.
     * @param message the message
     */
    public InvalidResponseException(String message) {
        super(ErrorCode.INVALID_RESPONSE, message);
    }

}
