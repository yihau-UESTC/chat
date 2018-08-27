package com.uestc.yihau.chat.server.module.player.service;

import com.uestc.yihau.chat.module.player.PlayerModule;
import com.uestc.yihau.chat.session.Session;

public interface PlayerService {
    PlayerModule.PlayerResponse registerAndLogin(Session session, String playerName, String password);

    PlayerModule.PlayerResponse login(Session session, String playerName, String password);
}
