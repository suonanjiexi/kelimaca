package com.snjx.kelimaca.server;

import com.corundumstudio.socketio.AckMode;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.JacksonJsonSupport;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.snjx.kelimaca.config.SocketIOServerConfig;
import com.snjx.kelimaca.listener.MyAuthorizationListener;
import com.snjx.kelimaca.listener.MyExceptionListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketIOServerInit {
    @Autowired
    private SocketIOServerConfig socketIOServerConfig;

    @Autowired
    private MyAuthorizationListener authorizationListener;

    @Autowired
    private MyExceptionListener exceptionListener;

    @Bean("socketIOServer")
    public SocketIOServer socketIOServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        socketConfig.setReuseAddress(true);
        socketConfig.setTcpKeepAlive(true);
        socketConfig.setTcpReceiveBufferSize(socketIOServerConfig.getTcpReceiveBufferSize());
        socketConfig.setTcpSendBufferSize(socketIOServerConfig.getTcpSendBufferSize());
        socketConfig.setAcceptBackLog(socketIOServerConfig.getAcceptBackLog());
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname(socketIOServerConfig.getHost());
        config.setPort(socketIOServerConfig.getPort());
        config.setContext(socketIOServerConfig.getContext());
        config.setJsonSupport(new JacksonJsonSupport());
        config.setUseLinuxNativeEpoll(false);
        config.setOrigin("*");
        config.setBossThreads(socketIOServerConfig.getBossCount());
        config.setWorkerThreads(socketIOServerConfig.getWorkCount());
        config.setAllowCustomRequests(socketIOServerConfig.isAllowCustomRequests());
        config.setUpgradeTimeout(socketIOServerConfig.getUpgradeTimeout());
        config.setPingTimeout(socketIOServerConfig.getPingTimeout());
        config.setPingInterval(socketIOServerConfig.getPingInterval());
        config.setWebsocketCompression(true);
        config.setAuthorizationListener(authorizationListener);
        config.setExceptionListener(exceptionListener);
        config.setMaxHttpContentLength(socketIOServerConfig.getMaxHttpContentLength());
        config.setMaxFramePayloadLength(socketIOServerConfig.getMaxFramePayloadLength());
        config.setRandomSession(true);
        //应答模式 AckMode.AUTO,AckMode.MANUAL,AckMode.AUTO_SUCCESS_ONLY
        config.setAckMode(AckMode.AUTO);
        //支持分布 使用Redis作为共享会话存储
        //config.setStoreFactory(new RedissonStoreFactory(redissonClient));
        return new SocketIOServer(config);
    }

}
