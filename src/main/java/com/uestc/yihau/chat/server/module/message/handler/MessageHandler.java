package com.uestc.yihau.chat.server.module.message.handler;

import com.uestc.yihau.chat.annotion.SocketCommand;
import com.uestc.yihau.chat.annotion.SocketMoudule;
import com.uestc.yihau.chat.model.Result;
import com.uestc.yihau.chat.module.ModuleId;
import com.uestc.yihau.chat.module.chat.ChatCmd;

@SocketMoudule(module = ModuleId.CHAT)
public interface MessageHandler {
    @SocketCommand(cmd = ChatCmd.PUBLIC_CHAT)
    public Result<?> publicMessage(long playerId, byte[] data);
    @SocketCommand(cmd = ChatCmd.PRIVATE_CHAT)
    public Result<?> privateMessage(long playerId, byte[] data);
}
