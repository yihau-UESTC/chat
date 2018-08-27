package com.uestc.yihau.chat.server.module.message.service;

public interface MessageService {
    public void publicMessage(long playerId, String content);

    public void privateMessage(long playerId, long targetId, String content);
}
