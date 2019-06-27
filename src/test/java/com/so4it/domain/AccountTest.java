package com.so4it.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

     @Test
    public void builder() {
         Account account0 = new Account.Builder().withId(0L).withBalance(0.0).build();
         Account account1 = new Account.Builder().withId(1L).withBalance(2.0).build();
         Account account2 = Account.builder().withId(2L).withBalance(4.0).build();

         Assert.assertEquals(Double.valueOf(0d),account0.getBalance());
         Assert.assertEquals(Double.valueOf(2d),account1.getBalance());
         Assert.assertEquals(Double.valueOf(4d),account2.getBalance());

    }

    @Test
    public void toString1() {
        Account account1 = new Account.Builder().withId(1L).withBalance(2.0).build();
        Assert.assertEquals("Account{id=1, balance=2.0}",account1.toString());
    }

}