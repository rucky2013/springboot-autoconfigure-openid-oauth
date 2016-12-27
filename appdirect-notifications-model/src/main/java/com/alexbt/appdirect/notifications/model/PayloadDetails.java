package com.alexbt.appdirect.notifications.model;

import java.util.HashMap;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alexbt.appdirect.notifications.jsr303.ValidationGroupCancel;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupChange;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupCreate;
import com.alexbt.appdirect.notifications.jsr303.ValidationGroupNotice;

/**
 * Payload details excluding the user
 * @author alexbt
 *
 */
public abstract class PayloadDetails {
    @Valid
    @NotNull(groups = { ValidationGroupChange.class, ValidationGroupCancel.class, ValidationGroupNotice.class })
    private Account account;

    @Valid
    private Company company;

    @Valid
    @NotNull(groups = { ValidationGroupCreate.class, ValidationGroupChange.class })
    private Order order;

    @Valid
    private AddonInstance addonInstance;

    @Valid
    @NotNull(groups = ValidationGroupNotice.class)
    private Notice notice;

    private HashMap<String, String> configuration;

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * @return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * @return the addonInstance
     */
    public AddonInstance getAddonInstance() {
        return addonInstance;
    }

    /**
     * @param addonInstance the addonInstance to set
     */
    public void setAddonInstance(AddonInstance addonInstance) {
        this.addonInstance = addonInstance;
    }

    /**
     * @return the notice
     */
    public Notice getNotice() {
        return notice;
    }

    /**
     * @param notice the notice to set
     */
    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    /**
     * @return the configuration
     */
    public HashMap<String, String> getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(HashMap<String, String> configuration) {
        this.configuration = configuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PayloadDetails [account=" + account + ", company=" + company + ", order=" + order + ", addonInstance=" + addonInstance + ", notice=" + notice
                + ", configuration=" + configuration + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((addonInstance == null) ? 0 : addonInstance.hashCode());
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((configuration == null) ? 0 : configuration.hashCode());
        result = prime * result + ((notice == null) ? 0 : notice.hashCode());
        result = prime * result + ((order == null) ? 0 : order.hashCode());
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
        PayloadDetails other = (PayloadDetails) obj;
        if (account == null) {
            if (other.account != null) {
                return false;
            }
        } else if (!account.equals(other.account)) {
            return false;
        }
        if (addonInstance == null) {
            if (other.addonInstance != null) {
                return false;
            }
        } else if (!addonInstance.equals(other.addonInstance)) {
            return false;
        }
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (configuration == null) {
            if (other.configuration != null) {
                return false;
            }
        } else if (!configuration.equals(other.configuration)) {
            return false;
        }
        if (notice == null) {
            if (other.notice != null) {
                return false;
            }
        } else if (!notice.equals(other.notice)) {
            return false;
        }
        if (order == null) {
            if (other.order != null) {
                return false;
            }
        } else if (!order.equals(other.order)) {
            return false;
        }
        return true;
    }

}
