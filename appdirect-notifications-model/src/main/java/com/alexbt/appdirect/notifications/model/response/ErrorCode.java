package com.alexbt.appdirect.notifications.model.response;

import com.alexbt.appdirect.notifications.util.Validate;

/**
 * Holds the available Error codes.
 * @author alexbt
 *
 */
public enum ErrorCode {
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS"), //
    USER_NOT_FOUND("USER_NOT_FOUND"), //
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND"), //
    MAX_USER_REACHED("MAX_USER_REACHED"), //
    UNAUTHORIZED("UNAUTHORIZED"), //
    OPERATION_CANCELED("OPERATION_CANCELED"), //
    CONFIGURATION_ERROR("CONFIGURATION_ERROR"), //
    INVALID_RESPONSE("INVALID_RESPONSE"), //
    PENDING("PENDING"), //
    FORBIDDEN("FORBIDDEN"), //
    BINDING_NOT_FOUND("BINDING_NOT_FOUND"), //
    TRANSPORT_ERROR("TRANSPORT_ERROR"), //
    UNKNOWN_ERROR("UNKNOWN_ERROR");

    private String id;

    ErrorCode(String id) {
        Validate.notEmpty(id);
        this.id = id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
