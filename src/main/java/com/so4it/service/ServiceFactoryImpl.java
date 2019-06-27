package com.so4it.service;

import org.springframework.context.support.GenericXmlApplicationContext;
import java.time.LocalDateTime;

public class ServiceFactoryImpl implements ServiceFactory{

    private AccountService accountService;

    @Override
    public AccountService createAccountService() {

        if (accountService != null) return accountService;

        System.out.println(LocalDateTime.now() + " Initiating the AccountService using ApplicationContext files ");

        GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext(
                "Messaging.xml","DaoTier.xml","ServiceTier.xml");

        accountService = applicationContext.getBean(AccountService.class);
        System.out.println(LocalDateTime.now() + " Initiated");

        return accountService;

        //return applicationContext.getBean(AccountService.class);
    }
}
