package com.so4it;

import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountDaoUnitTest {

    AccountDao accountDao;

    @Before
    public void initTests() { accountDao = new AccountDaoImpl(); }

    @Test
    public void testCreateAndRead() {
        accountDao.create(Account.builder().withId(1L).withBalance(2.0).build());
        Assert.assertNotNull(accountDao.read(1L));
    }

    @Test
    public void testReadAccountsWithLimitAbove() {
        accountDao.create(Account.builder().withId(1L).withBalance(101d).build());
        accountDao.create(Account.builder().withId(2L).withBalance(99d).build());
        accountDao.create(Account.builder().withId(3L).withBalance(100d).build());
        Assert.assertEquals(2,accountDao.readAccountsWithBalanceOver(99d).size());
    }

    @Test
    public void testNotSingleton() {
        AccountDao accountDao2 = new AccountDaoImpl();

        accountDao.create(Account.builder().withId(1L).withBalance(2.0).build());
        accountDao2.create(Account.builder().withId(1L).withBalance(4.0).build());

        Assert.assertNotEquals(accountDao.read(1L),accountDao2.read(1L));

    }
}
