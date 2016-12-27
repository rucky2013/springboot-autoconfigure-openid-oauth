package com.alexbt.autoconfigure.openid.properties;

/**
 * OpenId Provider (loaded from properties)
 * @author alexbt
 *
 */
public class OpenIdProvider {

    private String login;
    private String logout;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

}
