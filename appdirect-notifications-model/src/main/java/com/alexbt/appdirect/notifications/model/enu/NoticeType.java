package com.alexbt.appdirect.notifications.model.enu;

import com.alexbt.appdirect.notifications.util.Validate;

/**
 * Holds the available Notice types.
 * @author alexbt
 *
 */
public enum NoticeType {
    REACTIVATED("REACTIVATED"), //
    DEACTIVATED("DEACTIVATED"), //
    CLOSED("CLOSED"), //
    UPCOMING_INVOICE("UPCOMING_INVOICE");

    private String id;

    NoticeType(String id) {
        Validate.notEmpty(id);
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
