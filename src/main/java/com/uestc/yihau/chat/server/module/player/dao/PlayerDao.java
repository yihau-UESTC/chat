package com.uestc.yihau.chat.server.module.player.dao;

import com.uestc.yihau.chat.server.module.player.dao.entity.Player;

public interface PlayerDao {
    Player getPlayerById(long playerId);
    Player getPlayerByName(String playerName);
    Player insertPlayer(Player player);
}
