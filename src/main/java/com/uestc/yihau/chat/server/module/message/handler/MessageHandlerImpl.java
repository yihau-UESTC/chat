package com.uestc.yihau.chat.server.module.message.handler;

import com.uestc.yihau.chat.ErrorCodeException;
import com.uestc.yihau.chat.model.Result;
import com.uestc.yihau.chat.model.ResultCode;
import com.uestc.yihau.chat.module.chat.ChatModule;
import com.uestc.yihau.chat.server.module.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
@Component
public class MessageHandlerImpl implements MessageHandler{

    @Autowired
    private MessageService messageService;

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public Result<?> publicMessage(long playerId, byte[] data) {
        try{
            ChatModule.PublicMessageRequest publicMessageRequest = ChatModule.PublicMessageRequest.parseFrom(data);

            if (StringUtils.isEmpty(publicMessageRequest.getContext())){
                return Result.ERROR(ResultCode.AGRUMENT_ERROR);
            }
            messageService.publicMessage(playerId, publicMessageRequest.getContext());
        }catch (ErrorCodeException e){
            return Result.ERROR(e.getErrorCode());
        }catch (Exception e){
            return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
        }
        return Result.SUCCESS();
    }

    @Override
    public Result<?> privateMessage(long playerId, byte[] data) {
        return null;
    }
}
