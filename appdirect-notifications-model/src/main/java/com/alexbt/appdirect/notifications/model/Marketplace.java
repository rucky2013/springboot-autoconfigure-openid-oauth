package com.alexbt.appdirect.notifications.model;

import javax.validation.constraints.NotNull;

/**
 * Marketplace details.
 * @author alexbt
 *
 */
public class Marketplace {

    @NotNull
    private String partner;

    @NotNull
    private String baseUrl;

    /**
     * @return the partner
     */
    public String getPartner() {
        return partner;
    }

    /**
     * @param partner the partner to set
     */
    public void setPartner(String partner) {
        this.partner = partner;
    }

    /**
     * @return the baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @param baseUrl the baseUrl to set
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String toString() {
        return "MarketPlace [partner=" + partner + ", baseUrl=" + baseUrl + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((baseUrl == null) ? 0 : baseUrl.hashCode());
        result = prime * result + ((partner == null) ? 0 : partner.hashCode());
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
        Marketplace other = (Marketplace) obj;
        if (baseUrl == null) {
            if (other.baseUrl != null) {
                return false;
            }
        } else if (!baseUrl.equals(other.baseUrl)) {
            return false;
        }
        if (partner == null) {
            if (other.partner != null) {
                return false;
            }
        } else if (!partner.equals(other.partner)) {
            return false;
        }
        return true;
    }
}
