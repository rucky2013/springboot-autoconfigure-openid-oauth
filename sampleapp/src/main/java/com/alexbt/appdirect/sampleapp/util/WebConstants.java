package com.alexbt.appdirect.sampleapp.util;

/**
 * Shared constants
 * @author alexbt
 *
 */
public abstract class WebConstants {

    public static final String INTG_BASE_URI = "/api";
    public static final String CREATE_URI = INTG_BASE_URI + "/subscription/create/notification";
    public static final String CHANGE_URI = INTG_BASE_URI + "/subscription/change/notification";
    public static final String NOTICE_URI = INTG_BASE_URI + "/subscription/notice/notification";
    public static final String CANCEL_URI = INTG_BASE_URI + "/subscription/cancel/notification";
    public static final String USER_ASSIGN_URI = INTG_BASE_URI + "/user/assignment/notification";
    public static final String USER_UNASSIGN_URI = INTG_BASE_URI + "/user/unassignment/notification";

    public static final String MARKETPLACE_USER = "MARKETPLACE_APPDIRECT";
    public static final String WEB_USER = "USER_APPDIRECT";

    public static final String AUTHORITY_MARKETPLACE = "hasAuthority('" + MARKETPLACE_USER + "')";
    public static final String AUTHORITY_WEB_USER = "hasAuthority('" + WEB_USER + "')";

    public static final String WEB_BASE_URI = "/client";
    public static final String WEB_URI_CURRENT_USER = WEB_BASE_URI + "/user";
    public static final String WEB_URI_SUBSCRIPTIONS = WEB_BASE_URI + "/subscriptions";

}
