package com.uestc.yihau.chat.server.module.message.service;

import com.uestc.yihau.chat.module.ModuleId;
import com.uestc.yihau.chat.module.chat.ChatCmd;
import com.uestc.yihau.chat.module.chat.ChatModule;
import com.uestc.yihau.chat.server.module.player.dao.PlayerDao;
import com.uestc.yihau.chat.server.module.player.dao.entity.Player;
import com.uestc.yihau.chat.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
public class MessageServiceImpl implements MessageService {

    @Autowired
    private PlayerDao playerDao;

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public void publicMessage(long playerId, String content) {
        Player player = playerDao.getPlayerById(playerId);
        SessionManager sessionManager = SessionManager.getInstance();
        Set<Long> onlinePlayers = sessionManager.getOnlinePlayers();
        ChatModule.MessageResponse messageResponse = ChatModule.MessageResponse.newBuilder()
                .setSendPlayerId(playerId)
                .setPlayerName(player.getPlayerName())
                .setChatType(ChatModule.ChatType.PUBLIC)
                .setContent(content)
                .build();

        for (Long id : onlinePlayers){
            sessionManager.sendMessage(id, ModuleId.CHAT, ChatCmd.PUSH_CHAT, messageResponse);
        }
    }

    @Override
    public void privateMessage(long playerId, long targetId, String content) {

    }
}
