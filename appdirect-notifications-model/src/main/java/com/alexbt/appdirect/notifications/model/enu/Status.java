package com.alexbt.appdirect.notifications.model.enu;

import com.alexbt.appdirect.notifications.util.Validate;

/**
 * Holds the available Account statuses.
 * @author alexbt
 *
 */
public enum Status {
    INITIALIZED("INITIALIZED"), //
    FAILED("FAILED"), //
    FREE_TRIAL("FREE_TRIAL"), //
    FREE_TRIAL_EXPIRED("FREE_TRIAL_EXPIRED"), //
    ACTIVE("ACTIVE"), //
    SUSPENDED("SUSPENDED"), //
    CANCELLED("CANCELLED");

    private String id;

    Status(String id) {
        Validate.notEmpty(id);
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

}
