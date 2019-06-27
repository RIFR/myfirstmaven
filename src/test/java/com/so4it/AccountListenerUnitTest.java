package com.so4it;

import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;
import com.so4it.messaging.AccountConsumer;
import com.so4it.messaging.AccountListener;
import com.so4it.messaging.AccountListenerImpl;
import com.so4it.messaging.AccountProducer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import java.time.LocalDateTime;

public class AccountListenerUnitTest {



    @Test
    public void testProducerConsumer() throws Exception{

        BlockingDeque<Account> accounts = new LinkedBlockingDeque<>();

        AccountProducer accountProducer = new AccountProducer(accounts);


        //Lambda expression
        //AccountConsumer accountConsumer = new AccountConsumer(accounts, System.out::println);


        //Anonymous class
//        AccountListener accountListener = new AccountListener(){
//            @Override
//            public void onAccount(Account account) {
//                System.out.println("1: " +account);
//            }
//        };
        //AccountConsumer accountConsumer = new AccountConsumer(accounts, accountListener);


        //Another way of doing lambda
        List<AccountListener> accountListeners = new ArrayList<>();
        accountListeners.add(account -> System.out.println(LocalDateTime.now() + " " + account));
        accountListeners.add(new AccountListenerImpl(new AccountDaoImpl()));
        AccountConsumer accountConsumer = new AccountConsumer(accounts, accountListeners);




        new Thread(accountProducer).start();
        accountConsumer.init();

        Thread.sleep(5000);

    }
}
