package com.uestc.yihau.chat.server.module.player.handler;


import com.uestc.yihau.chat.ErrorCodeException;
import com.uestc.yihau.chat.model.Result;
import com.uestc.yihau.chat.model.ResultCode;
import com.uestc.yihau.chat.module.player.PlayerModule;
import com.uestc.yihau.chat.server.module.player.service.PlayerService;
import com.uestc.yihau.chat.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
@Component
public class PlayerHandlerImpl implements PlayerHandler {

    @Autowired
    private  PlayerService playerService;

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public Result<PlayerModule.PlayerResponse> registerAndLogin(Session session, byte[] data) {
        PlayerModule.PlayerResponse response = null;
       try {
           PlayerModule.RegisterRequest registerRequest = PlayerModule.RegisterRequest.parseFrom(data);
           if (StringUtils.isEmpty(registerRequest.getPlayerName()) || StringUtils.isEmpty(registerRequest.getPassword())){
               return Result.ERROR(ResultCode.PLAYERNAME_NULL);
           }
           response = playerService.registerAndLogin(session, registerRequest.getPlayerName(), registerRequest.getPassword());
       }catch (ErrorCodeException e){
           return Result.ERROR(e.getErrorCode());
       }catch (Exception e){
           e.printStackTrace();
           return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
       }
       return Result.SUCCESS(response);
    }

    @Override
    public Result<PlayerModule.PlayerResponse> login(Session session, byte[] data) {
            PlayerModule.PlayerResponse response = null;
            try {
                PlayerModule.LoginRequest loginRequest = PlayerModule.LoginRequest.parseFrom(data);
                if (StringUtils.isEmpty(loginRequest.getPlayerName()) || StringUtils.isEmpty(loginRequest.getPassword())){
                    return Result.ERROR(ResultCode.PLAYERNAME_NULL);
                }

                response = playerService.login(session, loginRequest.getPlayerName(), loginRequest.getPassword());
            }catch (ErrorCodeException e){
                return Result.ERROR(e.getErrorCode());
            }catch (Exception e){
                e.printStackTrace();
                return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
            }
            return Result.SUCCESS(response);
    }
}
