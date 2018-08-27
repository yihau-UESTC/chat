package test1;

import com.google.protobuf.InvalidProtocolBufferException;
import com.uestc.yihau.chat.module.chat.ChatModule;
import com.uestc.yihau.chat.server.module.player.dao.PlayerDao;
import com.uestc.yihau.chat.server.module.player.dao.entity.Player;
import com.uestc.yihau.chat.server.module.player.handler.PlayerHandler;
import com.uestc.yihau.chat.server.module.player.service.PlayerService;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class Test1 {

    @Test
    public void run(){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationConfig.xml");
        context.start();
        PlayerDao dao = (PlayerDao) context.getBean("playerDao");
        Player player = dao.getPlayerById(3);
        System.out.println(player);

        PlayerService service = (PlayerService) context.getBean("playerService");
        PlayerHandler handler = (PlayerHandler) context.getBean("playerHandler");
        System.out.println(handler);
    }
    @Test
    public void run1(){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationConfig.xml");
        context.start();
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        System.out.println(dataSource.toString());
    }

    @Test
    public void run2() throws InvalidProtocolBufferException {
        ChatModule.MessageResponse response = ChatModule.MessageResponse.newBuilder()
                .setSendPlayerId(123)
                .setPlayerName("yihau")
                .setTargetName("QQQ")
                .setChatType(ChatModule.ChatType.PUBLIC)
                .setContent("HELLO!")
                .build();
        byte[] bytes =  response.toByteArray();
        System.out.println(ChatModule.MessageResponse.parseFrom(bytes));
    }

}
