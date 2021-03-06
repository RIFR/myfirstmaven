package com.so4it.messaging;

import com.so4it.domain.Account;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.BlockingDeque;

public class AccountProducer implements Runnable{


    private static final Random RANDOM = new Random();


    private BlockingDeque<Account> queue;


    public AccountProducer(BlockingDeque<Account> queue) {
        this.queue = queue;
    }


    public void create(Account account){
        queue.add(account);
    }



    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                System.out.println(LocalDateTime.now() + " CREATED Account");
                queue.add(Account.builder().withId(RANDOM.nextLong()).withBalance(RANDOM.nextDouble()).build());
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
