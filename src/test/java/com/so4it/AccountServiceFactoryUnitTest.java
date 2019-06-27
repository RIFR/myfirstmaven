package com.so4it;

import com.so4it.domain.Account;
import com.so4it.service.*;
import com.so4it.util.Poller;
import com.so4it.util.SatisfiedWhenTrueReturned;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class AccountServiceFactoryUnitTest {

    AccountService accountService;

    @Test
    public void testGetBalanceHardcoded() throws Exception{

        System.out.println("Init 0    " + LocalDateTime.now());
        accountService = getBalanceDynamically("com.so4it.service.ServiceFactoryImpl");
        System.out.println("Initiated " + LocalDateTime.now());

        accountService.create(Account.builder().withId(1L).withBalance(100d).build());
        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create( () -> accountService.getBalance(1L) != 0d));
        Assert.assertEquals(accountService.getBalance(1L),Double.valueOf(100d));

        System.out.println();
        System.out.println("Init 1    " + LocalDateTime.now());
        accountService = getBalanceDynamically("com.so4it.service.ServiceFactoryImplOne");

        accountService.create(Account.builder().withId(1L).withBalance(100d).build());
        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create( () -> accountService.getBalance(1L) != 0d));
        Assert.assertEquals(accountService.getBalance(1L),Double.valueOf(100d));

        System.out.println();
        System.out.println("Init 2    " + LocalDateTime.now());
        accountService = getBalanceDynamically("com.so4it.service.ServiceFactoryImplTwo");

        accountService.create(Account.builder().withId(1L).withBalance(200d).build());
        Assert.assertEquals(accountService.getBalance(1L),Double.valueOf(0d));

        System.out.println();
        System.out.println("Init 3    " + LocalDateTime.now());
        accountService = getBalanceDynamically("com.so4it.service.ServiceFactoryImpl");
        System.out.println("Initiated " + LocalDateTime.now());

        accountService.create(Account.builder().withId(3L).withBalance(300d).build());
        Poller.pollAndCheck(SatisfiedWhenTrueReturned.create( () -> accountService.getBalance(3L) != 0d));
        Assert.assertEquals(accountService.getBalance(3L),Double.valueOf(300d));

        System.out.println("Ended     " + LocalDateTime.now());

        accountService.create(Account.builder().withId(4L).withBalance(400d).build());
//        for (int i = 0; i < 999; i++) {
//            if (accountService.getBalance(2L) != 0d)
//                {System.out.println(i); break;};
//        }

        Thread.sleep(25);
        Assert.assertEquals(accountService.getBalance(4L),Double.valueOf(400d));
        System.out.println("end  4    " + LocalDateTime.now());

        System.out.println(ServiceFactoryImplOne.getAtomicInteger());

        //System.out.println(getBalanceDynamically("com.so4it.service.ServiceFactoryImplOne").getBalance(1L));
        //System.out.println(getBalanceDynamically("com.so4it.service.ServiceFactoryImplTwo").getBalance(1L));
        //System.out.println(getBalanceDynamically("com.so4it.service.ServiceFactoryImpl").getBalance(1L));
    }

    public AccountService getBalanceDynamically(String clazz) throws Exception{
        ServiceFactory serviceFactory = (ServiceFactory)Class.forName(clazz).getDeclaredConstructor().newInstance();
        return serviceFactory.createAccountService();
    }

}
