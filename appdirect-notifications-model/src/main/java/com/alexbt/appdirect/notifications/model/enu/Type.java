package com.alexbt.appdirect.notifications.model.enu;

import java.util.Arrays;

import com.alexbt.appdirect.notifications.jsr303.AppdirectValidationGroup;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupCancel;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupChange;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupCreate;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupNotice;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupUserAdd;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupUserRemove;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupUserUpdated;
import com.alexbt.appdirect.notifications.util.Validate;

/**
 * Holds the available Notification types.
 * @author alexbt
 *
 */
public enum Type {
    SUBSCRIPTION_ORDER("SUBSCRIPTION_ORDER", ValidationGroupCreate.class), //
    SUBSCRIPTION_CHANGE("SUBSCRIPTION_CHANGE", ValidationGroupChange.class), //
    SUBSCRIPTION_CANCEL("SUBSCRIPTION_CANCEL", ValidationGroupCancel.class), //
    SUBSCRIPTION_NOTICE("SUBSCRIPTION_NOTICE", ValidationGroupNotice.class), //
    USER_ASSIGNMENT("USER_ASSIGNMENT", ValidationGroupUserAdd.class), //
    USER_UNASSIGNMENT("USER_UNASSIGNMENT", ValidationGroupUserRemove.class), //
    USER_UPDATED("USER_UPDATED", ValidationGroupUserUpdated.class);

    private String id;
    private Class<? extends AppdirectValidationGroup>[] validationGroups;

    @SafeVarargs
    Type(String id, Class<? extends AppdirectValidationGroup>... validationGroups) {
        Validate.notEmpty(id);
        Validate.notNull(validationGroups);
        this.id = id;
        this.validationGroups = validationGroups;
    }

    /**
     * Returns a defensive copy of the validation groups
     * @return the validation groups
     */
    public Class<? extends AppdirectValidationGroup>[] getValidationGroup() {
        // defensive copy
        return Arrays.copyOf(validationGroups, validationGroups.length);
    }

    @Override
    public String toString() {
        return id;
    }
}
