package com.alexbt.appdirect.sampleapp.notifications.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alexbt.appdirect.notifications.model.Account;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.alexbt.appdirect.notifications.model.response.ErrorCode;
import com.alexbt.appdirect.sampleapp.notifications.exception.AccountNotFoundException;

public abstract class AbstractExistingControllerTest extends AbstractControllerTest {

    @Test
    public void testNoErrorSanityCheck() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy"));

        verifyServiceInteractions();
        verifyNoMoreInteractions(subscriptionService);
    }

    @Test
    public void testAccountNotFound() throws Exception {
        doThrow(new AccountNotFoundException("message")).when(notificationValidate).accountExists(any(Account.class));

        assertErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, mockMvc.perform(MockMvcRequestBuilders //
                .get(getUrl() + "?url=http://blah.com/dummy")));
    }

    protected abstract void verifyServiceInteractions();

    protected abstract Type getType();

}
