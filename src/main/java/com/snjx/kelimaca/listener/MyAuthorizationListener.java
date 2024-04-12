package com.snjx.kelimaca.listener;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author by suonanjiexi
 * @version 1.0
 * @apiNote TODO
 * @description TODO
 * @date 2023-02-24 12:29
 */
@Component
@Slf4j
public class MyAuthorizationListener implements AuthorizationListener {
    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData handshakeData) {
        String authorization = handshakeData.getHttpHeaders().get("Authorization")==null?handshakeData.getSingleUrlParam("Authorization"):"";
        log.debug("isAuthorized Authorization {}",authorization);
        //校验token的合法性，实际业务需要校验token是否过期
        //如果认证不通过会返回一个 Socket.EVENT_CONNECT_ERROR 事件
      /*  if ("1".equals(authorization)){
            return true;
        }*/
        return new AuthorizationResult(true);
    }
}
