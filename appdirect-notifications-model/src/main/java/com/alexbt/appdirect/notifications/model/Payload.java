package com.alexbt.appdirect.notifications.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alexbt.appdirect.notifications.jsr303.ValidationGroupUserAdd;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupUserRemove;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupUserUpdated;

/**
 * Payload details.
 * @author alexbt
 *
 */
public class Payload extends PayloadDetails {
    @Valid
    @NotNull(groups = { //
            ValidationGroupUserAdd.class, //
            ValidationGroupUserRemove.class, //
            ValidationGroupUserUpdated.class })
    private User user;

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Payload [user=" + user + ", " + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Payload other = (Payload) obj;
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }
}
