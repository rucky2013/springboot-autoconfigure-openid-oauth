package com.alexbt.appdirect.notifications.model.response;

/**
 * Response interface implemented both for successful and error responses.
 * @author alexbt
 *
 */
public interface Response {

    /**
     * Flag indicating whether the response is for success or failure.
     * @return true if it's response for a successful processing, false otherwise.
     */
    boolean isSuccess();

}
