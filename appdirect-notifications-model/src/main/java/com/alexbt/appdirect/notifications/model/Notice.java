package com.alexbt.appdirect.notifications.model;

import javax.validation.constraints.NotNull;

import com.alexbt.appdirect.notifications.model.enu.NoticeType;

/**
 * Notice's type and message.
 * @author alexbt
 *
 */
public class Notice {

    @NotNull
    private NoticeType type;

    private String message;

    /**
     * @return the type
     */
    public NoticeType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(NoticeType type) {
        this.type = type;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notice [type=" + type + ", message=" + message + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Notice other = (Notice) obj;
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }
}
