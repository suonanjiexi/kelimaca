package com.snjx.kelimaca.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author by suonanjiexi
 * @version 1.0
 * @apiNote TODO
 * @description TODO
 * @date 2023-02-28 10:29
 */
@Component
@Slf4j
public class MyExceptionListener implements ExceptionListener {
    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
      log.error("onEventException {}",client.getSessionId(),e);
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
        log.error("onDisconnectException {}",client.getSessionId(),e);
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
        log.error("onConnectException {}",client.getSessionId(),e);
    }

    @Override
    public void onPingException(Exception e, SocketIOClient client) {
        log.error("onPingException {}",client.getSessionId(),e);
    }

    @Override
    public void onPongException(Exception e, SocketIOClient client) {
        log.error("onPongException {}",client.getSessionId(),e);
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e)  {
        log.error("exceptionCaught {}",ctx.channel().id(),e);
        return false;
    }

    @Override
    public void onAuthException(Throwable e, SocketIOClient client) {
        log.error("onAuthException {}",client.getSessionId(),e);
    }
}
