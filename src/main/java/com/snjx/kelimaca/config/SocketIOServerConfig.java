package com.snjx.kelimaca.config;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author snjx
 */
@Slf4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Configuration
public class SocketIOServerConfig {

    @Value("${socket.host}")
    String host;

    @Value("${socket.port}")
    Integer port;

    @Value("${socket.context}")
    String context;

    @Value("${socket.bossCount}")
    int bossCount;

    @Value("${socket.workCount}")
    int workCount;

    @Value("${socket.allowCustomRequests}")
    boolean allowCustomRequests;

    @Value("${socket.upgradeTimeout}")
    int upgradeTimeout;

    @Value("${socket.pingTimeout}")
    int pingTimeout;

    @Value("${socket.pingInterval}")
    int pingInterval;

    @Value("${socket.acceptBackLog}")
    int acceptBackLog;

    @Value("${socket.tcpReceiveBufferSize}")
    int tcpReceiveBufferSize;

    @Value("${socket.tcpSendBufferSize}")
    int tcpSendBufferSize;

    @Value("${socket.maxFramePayloadLength}")
    int maxFramePayloadLength;

    @Value("${socket.maxHttpContentLength}")
    int maxHttpContentLength;
}
