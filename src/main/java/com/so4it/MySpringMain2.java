package com.so4it;

import com.so4it.dao.AccountDao;
import com.so4it.domain.Account;
import com.so4it.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import java.util.Optional;

public class MySpringMain2 {


    public static void main(String[] args){

        GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext(
                "Messaging.xml",
                "DaoTier.xml",
                "ServiceTier.xml");
        try{
            AccountDao dao = applicationContext.getBean(AccountDao.class);
            Optional<Account> optional = dao.read(1L);
            System.out.println("isPresent:" + optional.isPresent());

            AccountService accountService = applicationContext.getBean(AccountService.class);

            accountService.create(Account.builder().withId(5L).withBalance(5.0d).build());
            accountService.create(Account.builder().withId(4L).withBalance(4.0d).build());
            accountService.create(Account.builder().withId(3L).withBalance(3.0d).build());
            accountService.create(Account.builder().withId(2L).withBalance(2.0d).build());
            accountService.create(Account.builder().withId(1L).withBalance(1.0d).build());


            //I do a wait here to make sure the producer/consumer pattern has gone through
            //before looking into the dao
            //Thread.sleep(1000L);
            optional = dao.read(1L);
            System.out.println("isPresent:" + optional.isPresent());

            System.out.println(accountService.getBalance(1L));
            System.out.println(accountService.getBalance(2L));
            System.out.println(accountService.getBalance(3L));
            System.out.println(accountService.getBalance(4L));
            System.out.println(accountService.getBalance(5L));

            //Thread.sleep(250L);

        }catch(Exception e){

        }finally {
            applicationContext.close();
        }


    }
}
