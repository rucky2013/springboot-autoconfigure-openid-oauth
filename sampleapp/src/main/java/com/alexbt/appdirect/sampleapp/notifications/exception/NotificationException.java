package com.alexbt.appdirect.sampleapp.notifications.exception;

import org.springframework.util.Assert;

import com.alexbt.appdirect.notifications.model.response.ErrorCode;

/**
 * Abstract Exception implemented by handled exception.
 * @author alexbt
 *
 */
public abstract class NotificationException extends RuntimeException {

    private static final long serialVersionUID = -1047627603866878056L;
    private ErrorCode errorCode;

    /**
     * Constructor.
     * @param errorCode the error code
     * @param message the message
     */
    public NotificationException(ErrorCode errorCode, String message) {
        super(message);
        Assert.notNull(errorCode);
        Assert.hasText(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "NotificationException [errorCode=" + errorCode + ", message=" + getMessage() + "]";
    }
}
