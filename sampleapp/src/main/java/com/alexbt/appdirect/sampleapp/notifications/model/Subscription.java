package com.alexbt.appdirect.sampleapp.notifications.model;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;

import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.PayloadDetails;
import com.alexbt.appdirect.notifications.model.User;

/**
 * Subscription model for mongodb
 * @author alexbt
 *
 */
public class Subscription extends PayloadDetails {

    @Id
    private String id;

    private String creatorUuid;

    private final Set<User> users = new LinkedHashSet<User>();

    private final List<Notification> notifications = new LinkedList<Notification>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatorUuid(String creatorUuid) {
        this.creatorUuid = creatorUuid;
    }

    /**
     * @return the creatorUuid
     */
    public String getCreatorUuid() {
        return creatorUuid;
    }

    /**
     * @return the users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Adds user to subscription.
     * @param user user to add
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Removes user from subscription.
     * @param user user to remove
     */
    public void removeUser(User user) {
        users.remove(user);
    }

    /**
     * Add notification to subscription (keeping all notifications attached to the subscription)
     * @param notification to add.
     */
    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Subscription [id=" + id + ", creatorUuid=" + creatorUuid + ", users=" + users + ", notifications=" + notifications + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((creatorUuid == null) ? 0 : creatorUuid.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((notifications == null) ? 0 : notifications.hashCode());
        result = prime * result + ((users == null) ? 0 : users.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
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
        Subscription other = (Subscription) obj;
        if (creatorUuid == null) {
            if (other.creatorUuid != null) {
                return false;
            }
        } else if (!creatorUuid.equals(other.creatorUuid)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (notifications == null) {
            if (other.notifications != null) {
                return false;
            }
        } else if (!notifications.equals(other.notifications)) {
            return false;
        }
        if (users == null) {
            if (other.users != null) {
                return false;
            }
        } else if (!users.equals(other.users)) {
            return false;
        }
        return true;
    }

}