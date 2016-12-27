package com.alexbt.appdirect.sampleapp.notifications.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alexbt.appdirect.notifications.model.Account;
import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.alexbt.appdirect.notifications.model.response.Response;
import com.alexbt.appdirect.notifications.model.response.SuccessResponse;
import com.alexbt.appdirect.sampleapp.util.WebConstants;

/**
 * User unassignment controller.
 * @author alexbt
 *
 */
@Controller
@RequestMapping(WebConstants.USER_UNASSIGN_URI)
public class UserUnassignController extends AbstractController {

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Response> handleNotification(Notification notification, Account account) {
        notificationValidate.userExists(notification.getPayload().getUser());
        notificationValidate.accountExists(account);
        subscriptionService.removeUser(notification, account);

        return new ResponseEntity<Response>(new SuccessResponse(account.getAccountIdentifier()), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Type getSupportedNotificationType() {
        return Type.USER_UNASSIGNMENT;
    }
}
