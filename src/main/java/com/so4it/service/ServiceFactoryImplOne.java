package com.so4it.service;
import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;
import com.so4it.messaging.AccountConsumer;
import com.so4it.messaging.AccountListener;
import com.so4it.messaging.AccountListenerImpl;
import com.so4it.messaging.AccountProducer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceFactoryImplOne implements ServiceFactory{

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    private AccountService accountService;

    @Override
    public AccountService createAccountService() {

        if (accountService != null) return accountService;

        System.out.println(LocalDateTime.now() + " Initiating the AccountService using new () ");

        AccountDao accountDao = new AccountDaoImpl();
        AccountListener accountListener = new AccountListenerImpl(accountDao);

        BlockingDeque<Account> accounts = new LinkedBlockingDeque<>();
        List<AccountListener> accountListeners = new ArrayList<>();

        accountListeners.add(account -> System.out.println(LocalDateTime.now() + " " + account));
        accountListeners.add(accountListener);
        //accountListeners.add(account -> accountDao.create(account));
        accountListeners.add(account -> atomicInteger.incrementAndGet());

        AccountConsumer accountConsumer = new AccountConsumer(accounts, accountListeners);
        accountConsumer.init();

        accountService = new AccountServiceImpl(accountDao, new AccountProducer(accounts));
        System.out.println(LocalDateTime.now() + " Initiated");

        return accountService;
    }

    static public int getAtomicInteger () {return atomicInteger.get();}

}
