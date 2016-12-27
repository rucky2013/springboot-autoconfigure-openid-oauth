package com.alexbt.appdirect.notifications.model.response;

import com.alexbt.appdirect.notifications.util.Validate;

/**
 * Response to return when an error occured.
 * @author alexbt
 *
 */
public class ErrorResponse implements Response {

    private ErrorCode errorCode;
    private String message;

    /**
     * Constructor.
     * @param errorCode the error code
     * @param message the error message
     */
    public ErrorResponse(ErrorCode errorCode, String message) {
        Validate.notNull(errorCode);
        Validate.notEmpty(message);

        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * @return the errorCode
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public String toString() {
        return "ErrorResponse [errorCode=" + errorCode + ", message=" + message + ", isSuccess()=" + isSuccess() + "]";
    }

}
