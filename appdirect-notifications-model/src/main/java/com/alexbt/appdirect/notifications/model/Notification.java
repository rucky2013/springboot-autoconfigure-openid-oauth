package com.alexbt.appdirect.notifications.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alexbt.appdirect.notifications.jsr303.ValidationGroupCreate;
import com.alexbt.appdirect.notifications.model.enu.Flag;
import com.alexbt.appdirect.notifications.model.enu.Type;

/**
 * Event notification.
 *
 * This notification is used to create/change/cancel/notice a subscription or add/remove/update a user from the
 * subscription.
 *
 * @author alexbt
 *
 */
public class Notification {

    @NotNull
    private Type type;

    @Valid
    @NotNull
    private Marketplace marketplace;

    private String applicationUuid;

    private Flag flag;
    private String returnUrl;

    @Valid
    @NotNull(groups = ValidationGroupCreate.class)
    private User creator;

    @Valid
    @NotNull
    private Payload payload;

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return the marketplace
     */
    public Marketplace getMarketplace() {
        return marketplace;
    }

    /**
     * @param marketplace the marketplace to set
     */
    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    /**
     * @return the applicationUuid
     */
    public String getApplicationUuid() {
        return applicationUuid;
    }

    /**
     * @param applicationUuid the applicationUuid to set
     */
    public void setApplicationUuid(String applicationUuid) {
        this.applicationUuid = applicationUuid;
    }

    /**
     * @return the flag
     */
    public Flag getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    /**
     * @return the returnUrl
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * @param returnUrl the returnUrl to set
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    /**
     * @return the creator
     */
    public User getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(User creator) {
        this.creator = creator;
    }

    /**
     * @return the payload
     */
    public Payload getPayload() {
        return payload;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Notification [type=" + type + ", marketplace=" + marketplace + ", applicationUuid=" + applicationUuid + ", flag=" + flag + ", returnUrl="
                + returnUrl + ", creator=" + creator + ", payload=" + payload + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((applicationUuid == null) ? 0 : applicationUuid.hashCode());
        result = prime * result + ((creator == null) ? 0 : creator.hashCode());
        result = prime * result + ((flag == null) ? 0 : flag.hashCode());
        result = prime * result + ((marketplace == null) ? 0 : marketplace.hashCode());
        result = prime * result + ((payload == null) ? 0 : payload.hashCode());
        result = prime * result + ((returnUrl == null) ? 0 : returnUrl.hashCode());
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
        Notification other = (Notification) obj;
        if (applicationUuid == null) {
            if (other.applicationUuid != null) {
                return false;
            }
        } else if (!applicationUuid.equals(other.applicationUuid)) {
            return false;
        }
        if (creator == null) {
            if (other.creator != null) {
                return false;
            }
        } else if (!creator.equals(other.creator)) {
            return false;
        }
        if (flag != other.flag) {
            return false;
        }
        if (marketplace == null) {
            if (other.marketplace != null) {
                return false;
            }
        } else if (!marketplace.equals(other.marketplace)) {
            return false;
        }
        if (payload == null) {
            if (other.payload != null) {
                return false;
            }
        } else if (!payload.equals(other.payload)) {
            return false;
        }
        if (returnUrl == null) {
            if (other.returnUrl != null) {
                return false;
            }
        } else if (!returnUrl.equals(other.returnUrl)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }
}
