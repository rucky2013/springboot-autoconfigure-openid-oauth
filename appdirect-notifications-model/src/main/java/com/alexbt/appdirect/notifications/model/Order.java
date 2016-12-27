package com.alexbt.appdirect.notifications.model;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Order details.
 * @author alexbt
 *
 */
public class Order {

    @NotNull
    private String editionCode;

    private String addonOfferingCode;

    @NotNull
    private String pricingDuration;

    private List<Item> items;

    /**
     * @return the editionCode
     */
    public String getEditionCode() {
        return editionCode;
    }

    /**
     * @param editionCode the editionCode to set
     */
    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    /**
     * @return the addonOfferingCode
     */
    public String getAddonOfferingCode() {
        return addonOfferingCode;
    }

    /**
     * @param addonOfferingCode the addonOfferingCode to set
     */
    public void setAddonOfferingCode(String addonOfferingCode) {
        this.addonOfferingCode = addonOfferingCode;
    }

    /**
     * @return the pricingDuration
     */
    public String getPricingDuration() {
        return pricingDuration;
    }

    /**
     * @param pricingDuration the pricingDuration to set
     */
    public void setPricingDuration(String pricingDuration) {
        this.pricingDuration = pricingDuration;
    }

    /**
     * @return the item
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Order [editionCode=" + editionCode + ", addonOfferingCode=" + addonOfferingCode + ", pricingDuration=" + pricingDuration + ", items=" + items
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addonOfferingCode == null) ? 0 : addonOfferingCode.hashCode());
        result = prime * result + ((editionCode == null) ? 0 : editionCode.hashCode());
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        result = prime * result + ((pricingDuration == null) ? 0 : pricingDuration.hashCode());
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
        Order other = (Order) obj;
        if (addonOfferingCode == null) {
            if (other.addonOfferingCode != null) {
                return false;
            }
        } else if (!addonOfferingCode.equals(other.addonOfferingCode)) {
            return false;
        }
        if (editionCode == null) {
            if (other.editionCode != null) {
                return false;
            }
        } else if (!editionCode.equals(other.editionCode)) {
            return false;
        }
        if (items == null) {
            if (other.items != null) {
                return false;
            }
        } else if (!items.equals(other.items)) {
            return false;
        }
        if (pricingDuration == null) {
            if (other.pricingDuration != null) {
                return false;
            }
        } else if (!pricingDuration.equals(other.pricingDuration)) {
            return false;
        }
        return true;
    }
}
