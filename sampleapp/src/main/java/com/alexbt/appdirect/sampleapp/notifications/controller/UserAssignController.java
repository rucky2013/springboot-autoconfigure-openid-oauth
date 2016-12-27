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
 * User assignment controller.
 * @author alexbt
 *
 */
@Controller
@RequestMapping(WebConstants.USER_ASSIGN_URI)
public class UserAssignController extends AbstractController {

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Response> handleNotification(Notification notification, Account account) {
        notificationValidate.isNewUser(notification.getPayload().getUser());
        notificationValidate.accountExists(account);
        subscriptionService.addUser(notification, account);

        return new ResponseEntity<Response>(new SuccessResponse(account.getAccountIdentifier()), HttpStatus.OK);
    }

    @Override
    protected Type getSupportedNotificationType() {
        return Type.USER_ASSIGNMENT;
    }
}
