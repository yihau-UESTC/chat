package com.uestc.yihau.chat.server.module.player.service;

import com.uestc.yihau.chat.ErrorCodeException;
import com.uestc.yihau.chat.model.ResultCode;
import com.uestc.yihau.chat.module.player.PlayerModule;
import com.uestc.yihau.chat.server.module.player.dao.PlayerDao;
import com.uestc.yihau.chat.server.module.player.dao.entity.Player;
import com.uestc.yihau.chat.session.Session;
import com.uestc.yihau.chat.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private  PlayerDao playerDao ;

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public PlayerModule.PlayerResponse registerAndLogin(Session session, String playerName, String password) {
        Player existplayer = playerDao.getPlayerByName(playerName);
        if (existplayer != null){
            throw new ErrorCodeException(ResultCode.PLAYER_EXIST);
        }


        Player player = new Player();
        player.setPlayerName(playerName);
        player.setPassword(password);
        player = playerDao.insertPlayer(player);
        return login(session, playerName, password);
    }

    @Override
    public PlayerModule.PlayerResponse login(Session session, String playerName, String password) {


        SessionManager sessionManager = SessionManager.getInstance();
        if (session.getAttachment() != null){
            throw new ErrorCodeException(ResultCode.HAS_LOGIN);
        }
        Player player = playerDao.getPlayerByName(playerName);
        if (player == null){
            throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
        }

        if (!player.getPassword().equals(password)){
            throw new ErrorCodeException(ResultCode.PASSWARD_ERROR);
        }
        boolean onlinePlayer = sessionManager.isOnlinePlayer(player.getPlayerId());
        if (onlinePlayer){
            Session oldSession = sessionManager.removeSession(player.getPlayerId());
            oldSession.removeAttachment();
            oldSession.close();
        }
        if (sessionManager.putSession(player.getPlayerId(), session)){
            session.setAttachment(player);
        }else {
            throw new ErrorCodeException(ResultCode.LOGIN_FAIL);
        }

        PlayerModule.PlayerResponse playerResponse = PlayerModule.PlayerResponse.newBuilder()
                .setPlayerId(player.getPlayerId())
                .setPlayerName(player.getPlayerName())
                .setLevel(player.getLevel())
                .setExp(player.getExp())
                .build();

        return playerResponse;
    }
}
