package com.so4it.service;

import com.so4it.dao.AccountDao;
//import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;
import com.so4it.messaging.AccountProducer;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;

public class ServiceFactoryImplTwo implements ServiceFactory{

    private AccountService accountService;

    @Override
    public AccountService createAccountService() {

        if (accountService != null) return accountService;

        System.out.println(LocalDateTime.now() + " Initiating the AccountService using MockUp ");
        accountService = new AccountServiceImpl(new AccountDaoMock(),new AccountProducer(new LinkedBlockingDeque<>()));
        System.out.println(LocalDateTime.now() + " Initiated");

        return accountService;
    }

    public static class AccountDaoMock implements AccountDao{

        @Override
        public void create(Account account) {

        }

        @Override
        public Optional<Account> read(Long id) {
            return Optional.empty();
        }

        @Override
        public Collection<Account> readAccountsWithBalanceOver(Double limit) {
            return null;
        }
    }

}
