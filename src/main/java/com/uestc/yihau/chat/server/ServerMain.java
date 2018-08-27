package com.uestc.yihau.chat.server;

import com.uestc.yihau.chat.server.module.player.dao.PlayerDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationConfig.xml");

        new Server().start();
    }
}
