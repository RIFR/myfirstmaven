package com.so4it;

import com.so4it.domain.Account;
import com.so4it.service.AccountService;
import com.so4it.service.ServiceFactory;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

public class MyMain {

   public static void main(String[] args) throws Exception {

        try {
            System.out.println(LocalDateTime.now() + " serviceFactory init");

            ServiceFactory serviceFactory = (ServiceFactory)Class.forName(args[0]).getDeclaredConstructor().newInstance();

            System.out.println(LocalDateTime.now() + " create first account");

            serviceFactory.createAccountService().create(Account.builder().withId(1L).withBalance(100d).build());

            System.out.println(LocalDateTime.now() + " create second & third account");
            serviceFactory.createAccountService().create(Account.builder().withId(2L).withBalance(200d).build());
            serviceFactory.createAccountService().create(Account.builder().withId(3L).withBalance(300d).build());

            System.out.println(LocalDateTime.now() + " sleeping 25 ms");

            Thread.sleep(25);

            System.out.println(LocalDateTime.now() + " getBalance");

            System.out.println(LocalDateTime.now() + " " + serviceFactory.createAccountService().getBalance(1L));
            System.out.println(LocalDateTime.now() + " " + serviceFactory.createAccountService().getBalance(2L));
            System.out.println(LocalDateTime.now() + " " + serviceFactory.createAccountService().getBalance(3L));

            System.out.println(LocalDateTime.now() + " finished");

         } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
