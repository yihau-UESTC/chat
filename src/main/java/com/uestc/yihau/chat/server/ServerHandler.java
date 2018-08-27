package com.uestc.yihau.chat.server;

import com.google.protobuf.GeneratedMessageV3;
import com.uestc.yihau.chat.model.Request;
import com.uestc.yihau.chat.model.Response;
import com.uestc.yihau.chat.model.Result;
import com.uestc.yihau.chat.model.ResultCode;
import com.uestc.yihau.chat.module.ModuleId;
import com.uestc.yihau.chat.serial.Serializer;
import com.uestc.yihau.chat.server.module.player.dao.entity.Player;
import com.uestc.yihau.chat.scanner.Invoker;
import com.uestc.yihau.chat.scanner.InvokerHolder;
import com.uestc.yihau.chat.session.Session;
import com.uestc.yihau.chat.session.SessionImpl;
import com.uestc.yihau.chat.session.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHandler extends SimpleChannelInboundHandler<Request> {

    public static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        System.out.println("serverHandler===============" + Thread.currentThread().getName());
        handlerMessage(new SessionImpl(ctx.channel()), msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = new SessionImpl(ctx.channel());
        Object object = session.getAttachment();
        if (object != null){
            Player player = (Player) object;
            SessionManager.getInstance().removeSession(player.getPlayerId());
            System.out.println(player.getPlayerName() + "has ofline");
        }
    }

    private void handlerMessage(SessionImpl session, Request msg) {
        Response response = new Response(msg.getModule(), msg.getCmd());
        System.out.println("module: " + msg.getModule() + " cmd: " + msg.getCmd());

        Invoker invoker = InvokerHolder.getInvoker(msg.getModule(), msg.getCmd());

        if (invoker != null){
            try {
                Result<?> result = null;
                if (msg.getModule() == ModuleId.PLAYER){
                    result = (Result<?>) invoker.invoke(session,msg.getData());
                }else {
                    Object attachment = session.getAttachment();
                    if (attachment != null){
                        Player player = (Player) attachment;
                        result = (Result<?>) invoker.invoke(player.getPlayerId(),msg.getData());
                    }else {
                        response.setStateCode(ResultCode.LOGIN_PLEASE);
                        session.write(response);
                        return;
                    }
                }
                if (result.getResultCode() == ResultCode.SUCCESS){
                    Object object = result.getContent();
                    if (object != null){
                        if (object instanceof GeneratedMessageV3){
                            GeneratedMessageV3 content = (GeneratedMessageV3) object;
                            response.setData(content.toByteArray());
                        }else {
                            System.out.println(String.format("不可识别传输对象:%s", object));
                        }
                    }
                    session.write(response);
                }else {
                    response.setStateCode(result.getResultCode());
                    session.write(response);
                    return;
                }
            }catch (Exception e){
                e.printStackTrace();
                response.setStateCode(ResultCode.UNKOWN_EXCEPTION);
                session.write(response);
            }
        }else {
            response.setStateCode(ResultCode.NO_INVOKER);
            System.out.println("no invoker");
            session.write(response);
            return;
        }
    }
}
