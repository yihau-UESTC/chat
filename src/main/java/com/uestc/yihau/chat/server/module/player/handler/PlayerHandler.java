package com.uestc.yihau.chat.server.module.player.handler;

import com.uestc.yihau.chat.annotion.SocketCommand;
import com.uestc.yihau.chat.annotion.SocketMoudule;
import com.uestc.yihau.chat.model.Result;
import com.uestc.yihau.chat.module.ModuleId;
import com.uestc.yihau.chat.module.player.PlayerCmd;
import com.uestc.yihau.chat.module.player.PlayerModule;
import com.uestc.yihau.chat.session.Session;

@SocketMoudule(module = ModuleId.PLAYER)
public interface PlayerHandler {

    @SocketCommand(cmd = PlayerCmd.REGISTER_AND_LOGIN)
    public Result<PlayerModule.PlayerResponse> registerAndLogin(Session session, byte[] data);

    @SocketCommand(cmd = PlayerCmd.LOGIN)
    public Result<PlayerModule.PlayerResponse> login(Session session, byte[] data);
}
