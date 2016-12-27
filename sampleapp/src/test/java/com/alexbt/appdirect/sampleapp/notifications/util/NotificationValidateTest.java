package com.alexbt.appdirect.sampleapp.notifications.util;

import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.alexbt.appdirect.notifications.jsr303.BeanValidatorUtil;
import com.alexbt.appdirect.notifications.model.Account;
import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.User;
import com.alexbt.appdirect.notifications.model.enu.Flag;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.alexbt.appdirect.sampleapp.notifications.dao.SubscriptionRepository;
import com.alexbt.appdirect.sampleapp.notifications.exception.AccountNotFoundException;
import com.alexbt.appdirect.sampleapp.notifications.exception.InvalidResponseException;
import com.alexbt.appdirect.sampleapp.notifications.exception.UnauthorizedException;
import com.alexbt.appdirect.sampleapp.notifications.exception.UserAlreadyExistException;
import com.alexbt.appdirect.sampleapp.notifications.exception.UserNotFoundException;
import com.alexbt.appdirect.sampleapp.notifications.model.Subscription;

@RunWith(SpringRunner.class)
public class NotificationValidateTest {

    @SpyBean
    private NotificationValidate notificationValidate;

    @MockBean
    private SubscriptionRepository repository;

    @MockBean
    private BeanValidatorUtil beanValidatorUtil;

    @MockBean
    private Validator validator;

    @Test
    public void testIsNewUser() {
        given(repository.findOneByAccountAccountIdentifier(anyString())).willReturn(null);
        notificationValidate.isNewUser(mock(User.class));
    }

    @Test(expected = UserAlreadyExistException.class)
    public void testIsNotNewUser() {
        given(repository.findOneByUsersUuid(anyString())).willReturn(new Subscription());
        notificationValidate.isNewUser(mock(User.class));
        fail(); // should not reach this point
    }

    @Test(expected = UserNotFoundException.class)
    public void testUserNotExists() {
        given(repository.findOneByUsersUuid(anyString())).willReturn(null);
        notificationValidate.userExists(mock(User.class));
        fail(); // should not reach this point
    }

    @Test
    public void testUserExists() {
        given(repository.findOneByUsersUuid(anyString())).willReturn(new Subscription());
        notificationValidate.userExists(mock(User.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsNewUserNull() {
        notificationValidate.isNewUser(null);
        fail(); // should not reach this point
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserExistsNull() {
        notificationValidate.userExists(null);
        fail(); // should not reach this point
    }

    @Test
    public void testAccountExists() {
        given(repository.findOneByAccountAccountIdentifier(anyString())).willReturn(new Subscription());
        notificationValidate.accountExists(mock(Account.class));
    }

    @Test(expected = AccountNotFoundException.class)
    public void testAccountNotExists() {
        given(repository.findOneByAccountAccountIdentifier(anyString())).willReturn(null);
        notificationValidate.accountExists(mock(Account.class));
        fail(); // should not reach this point
    }

    @Test(expected = UnauthorizedException.class)
    public void testIsNotNewAccount() {
        given(repository.findOneByAccountAccountIdentifier(anyString())).willReturn(new Subscription());
        notificationValidate.isNewAccount(mock(Account.class));
        fail(); // should not reach this point
    }

    @Test
    public void testIsNewAccount() {
        given(repository.findOneByAccountAccountIdentifier(anyString())).willReturn(null);
        notificationValidate.isNewAccount(mock(Account.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsNewAccountNull() {
        notificationValidate.isNewAccount(null);
        fail(); // should not reach this point
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAccountExistsNull() {
        notificationValidate.accountExists(null);
        fail(); // should not reach this point
    }

    @Test
    public void testValidFormat() {
        given(beanValidatorUtil.validate(any(Notification.class))).willReturn(Collections.<ConstraintViolation<Notification>>emptySet());
        notificationValidate.hasAllRequiredFields(mock(Notification.class));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = InvalidResponseException.class)
    public void testMissingType() {
        Set<ConstraintViolation<Notification>> constraints = new HashSet<ConstraintViolation<Notification>>();
        constraints.add((mock(ConstraintViolation.class)));
        given(beanValidatorUtil.validate(any(Notification.class))).willReturn(constraints);

        notificationValidate.hasAllRequiredFields(mock(Notification.class));

        fail(); // should not reach this point
    }

    @Test(expected = InvalidResponseException.class)
    public void isExpectedTypeBadTest() {
        notificationValidate.isExpectedType(Type.SUBSCRIPTION_CANCEL, Type.SUBSCRIPTION_CHANGE);
        fail(); // should not reach this point
    }

    @Test
    public void isExpectedTypeOkTest() {
        notificationValidate.isExpectedType(Type.SUBSCRIPTION_CHANGE, Type.SUBSCRIPTION_CHANGE);
    }

    @Test(expected = UnauthorizedException.class)
    public void isStatelessTest() {
        notificationValidate.isNotStateless(Flag.STATELESS);
        fail(); // should not reach this point
    }

    @Test
    public void isNotStatelessTest() {
        notificationValidate.isNotStateless(Flag.DEVELOPMENT);
    }
}
