package com.alexbt.appdirect.sampleapp.notifications.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.alexbt.appdirect.notifications.model.Account;
import com.alexbt.appdirect.notifications.model.Notice;
import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.Order;
import com.alexbt.appdirect.notifications.model.Payload;
import com.alexbt.appdirect.notifications.model.User;
import com.alexbt.appdirect.notifications.model.enu.NoticeType;
import com.alexbt.appdirect.notifications.model.enu.Status;
import com.alexbt.appdirect.sampleapp.notifications.dao.SubscriptionRepository;
import com.alexbt.appdirect.sampleapp.notifications.dao.SubscriptionRepositoryStub;
import com.alexbt.appdirect.sampleapp.notifications.model.Subscription;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Spy
    private SubscriptionRepository repository = new SubscriptionRepositoryStub();

    @Mock
    private Notification notification;

    private Subscription subscription1;
    private Subscription subscription2;

    @Before
    public void before() {
        repository.deleteAll();

        Account account1 = new Account("accId-1", Status.ACTIVE);
        Account account2 = new Account("accId-2", Status.ACTIVE);

        User user1 = new User();
        user1.setUuid("my-test-uuid-1");

        User user2 = new User();
        user2.setUuid("my-test-uuid-2");

        given(notification.getPayload()).willReturn(mock(Payload.class));
        given(notification.getPayload().getNotice()).willReturn(mock(Notice.class));
        given(notification.getCreator()).willReturn(mock(User.class));
        given(notification.getPayload().getAccount()).willReturn(mock(Account.class));
        given(notification.getPayload().getAccount().getAccountIdentifier()).willReturn("accId-1");

        subscription1 = new Subscription();
        subscription1.setId("1");
        subscription1.setAccount(account1);
        subscription1.addUser(user1);
        repository.save(subscription1);

        subscription2 = new Subscription();
        subscription2.setId("2");
        subscription2.setAccount(account2);
        subscription2.addUser(user2);
        repository.save(subscription2);

    }

    @Test
    public void testCancelExisting() {
        assertEquals(subscription1, repository.findOneByAccountAccountIdentifier(notification.getPayload().getAccount().getAccountIdentifier()));
        subscriptionService.cancel(notification, notification.getPayload().getAccount());

        assertEquals(1, repository.findAll().size());
        assertEquals(null, repository.findOneByAccountAccountIdentifier(notification.getPayload().getAccount().getAccountIdentifier()));
    }

    @Test
    public void testCancelNonExisting() {
        given(notification.getPayload().getAccount().getAccountIdentifier()).willReturn("non-existing-id");

        assertEquals(null, repository.findOneByAccountAccountIdentifier(notification.getPayload().getAccount().getAccountIdentifier()));
        subscriptionService.cancel(notification, notification.getPayload().getAccount());

        assertEquals(2, repository.findAll().size());
        assertEquals(null, repository.findOneByAccountAccountIdentifier(notification.getPayload().getAccount().getAccountIdentifier()));
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUuid("newUserUuid");
        given(notification.getPayload().getUser()).willReturn(user);

        subscriptionService.addUser(notification, notification.getPayload().getAccount());
        Subscription subscription = repository.findOneByAccountAccountIdentifier("accId-1");
        assertEquals(2, subscription.getUsers().size());
        assertTrue(subscription.getUsers().toString().contains("newUserUuid"));
    }

    @Test
    public void testRemoveUser() {
        User user = new User();
        user.setUuid("my-test-uuid-1");
        given(notification.getPayload().getUser()).willReturn(user);

        subscriptionService.removeUser(notification, notification.getPayload().getAccount());

        Subscription subscription = repository.findOneByAccountAccountIdentifier("accId-1");
        assertTrue(subscription.getUsers().isEmpty());
    }

    @Test
    public void testCreate() {
        repository.deleteAll();
        subscriptionService.create(notification, notification.getPayload().getAccount());

        assertTrue(repository.findOneByAccountAccountIdentifier("accId-1") != null);
    }

    @Test
    public void testUpdate() {
        given(notification.getReturnUrl()).willReturn("returnUrl");
        Order order = mock(Order.class);
        given(notification.getPayload().getOrder()).willReturn(order);
        subscriptionService.update(notification, notification.getPayload().getAccount());

        assertEquals(order, repository.findOneByAccountAccountIdentifier("accId-1").getOrder());
    }

    @Test
    public void testNotice() {
        given(notification.getPayload().getNotice().getType()).willReturn(NoticeType.UPCOMING_INVOICE);

        subscriptionService.notice(notification, notification.getPayload().getAccount());
        assertEquals(NoticeType.UPCOMING_INVOICE, repository.findOneByAccountAccountIdentifier("accId-1").getNotice().getType());
    }

    @Test
    public void testNoticeCancel() {
        given(notification.getPayload().getNotice().getType()).willReturn(NoticeType.CLOSED);
        subscriptionService.notice(notification, notification.getPayload().getAccount());
        assertEquals(null, repository.findOneByAccountAccountIdentifier("accId-1"));
    }
}
