package com.uestc.yihau.chat.scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationConfig.xml");
        Invoker invoker = InvokerHolder.getInvoker((short) 1,(short) 2);
        if (invoker != null) {
            invoker.invoke(null);
        }else {
            System.out.println("invoke is null");
        }
    }
}
