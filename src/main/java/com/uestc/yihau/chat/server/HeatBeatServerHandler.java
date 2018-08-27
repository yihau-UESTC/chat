package com.uestc.yihau.chat.server;

import com.uestc.yihau.chat.model.Request;
import com.uestc.yihau.chat.module.ModuleId;
import com.uestc.yihau.chat.server.module.player.dao.entity.Player;
import com.uestc.yihau.chat.session.Session;
import com.uestc.yihau.chat.session.SessionImpl;
import com.uestc.yihau.chat.session.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeatBeatServerHandler extends SimpleChannelInboundHandler<Request> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        if (msg.getModule() == ModuleId.HEATBEAT){
            System.out.println("server receive heatbeat --->" + ctx.channel().remoteAddress());
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //TODO: 处理不同类型的异常。
        cause.printStackTrace();
        Session session = new SessionImpl(ctx.channel());
        Object attachment = session.getAttachment();
        if (attachment != null){
            Player player = (Player) attachment;
            SessionManager.getInstance().removeSession(player.getPlayerId());
            System.out.println(player.getPlayerId() + "read time out, remove session, close ctx");
        }
        ctx.close();
    }
}
