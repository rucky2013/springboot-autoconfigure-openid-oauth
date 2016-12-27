package com.alexbt.appdirect.notifications.model.enu;

import com.alexbt.appdirect.notifications.util.Validate;

/**
 * Holds the available Notification flags.
 * @author alexbt
 *
 */
public enum Flag {
    STATELESS("STATELESS"), //
    DEVELOPMENT("DEVELOPMENT");

    private String id;

    Flag(String id) {
        Validate.notEmpty(id);
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
