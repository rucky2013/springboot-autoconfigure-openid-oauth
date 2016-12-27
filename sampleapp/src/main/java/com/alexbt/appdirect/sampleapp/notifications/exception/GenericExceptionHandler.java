package com.alexbt.appdirect.sampleapp.notifications.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alexbt.appdirect.notifications.model.response.ErrorCode;
import com.alexbt.appdirect.notifications.model.response.ErrorResponse;
import com.alexbt.appdirect.notifications.model.response.Response;
import com.alexbt.appdirect.sampleapp.Launcher;

/**
 * Catch all exceptions and return ErrorResponse.
 * @author alexbt
 *
 */
@ControllerAdvice(basePackageClasses = Launcher.class)
public class GenericExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @ExceptionHandler
    ResponseEntity<Response> handleUnexpectedRuntime(RuntimeException e) {
        LOGGER.debug("Unexpected error", e);
        ResponseEntity<Response> responseEntity = new ResponseEntity<Response>( //
                new ErrorResponse(ErrorCode.UNKNOWN_ERROR, "An unknown error occured"), HttpStatus.OK);
        LOGGER.debug("Response: {}", responseEntity);
        return responseEntity;
    }

    @ExceptionHandler
    ResponseEntity<Response> handleNotificationException(NotificationException e) {
        LOGGER.debug("Handling error: {}", String.valueOf(e));
        ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(new ErrorResponse(e.getErrorCode(), e.getMessage()), HttpStatus.OK);
        LOGGER.debug("Response: {}", responseEntity);
        return responseEntity;
    }

    @ExceptionHandler
    ResponseEntity<Response> handleAccessDenied(AccessDeniedException e) {
        LOGGER.debug("Handling error: {}", String.valueOf(e));
        ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(HttpStatus.UNAUTHORIZED);
        LOGGER.debug("Response: {}", responseEntity);
        return responseEntity;
    }

}
