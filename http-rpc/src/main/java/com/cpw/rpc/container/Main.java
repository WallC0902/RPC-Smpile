package com.cpw.rpc.container;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wufeng on 2016/05/08.
 */
public class Main
{
    public static void main(String[] args) throws Exception
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
        context.start();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
}
