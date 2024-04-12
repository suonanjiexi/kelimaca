package com.snjx.kelimaca.handler;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.snjx.kelimaca.cache.ClientCache;
import com.snjx.kelimaca.event.Event;
import com.snjx.kelimaca.model.MessageModel;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.internal.PlatformDependent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@Component
public class SocketHandler {
    public static final Map<String,SocketIOClient> SINGLE_CLIENT = PlatformDependent.newConcurrentHashMap();

    public static final Map<String,List<SocketIOClient>> GROUP_CLIENT = PlatformDependent.newConcurrentHashMap();

    @Resource
    private ClientCache clientCache;

    @OnConnect
    public void onConnect(SocketIOClient client) {
        String clientId = getClientId(client);
        SINGLE_CLIENT.put(clientId, client);

        String groupId = getGroupId(client);
        GROUP_CLIENT.put(groupId,getGroup(client));
        client.sendEvent(Event.LOGIN_EVENT.getEvent(), "嗨," + clientId + " 你好! 建立连接");
        log.info("客户端: {} 已连接,clientId  {}",client.getSessionId(), clientId);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String clientId = getClientId(client);
        SINGLE_CLIENT.remove(clientId);
        String groupId = getGroupId(client);
        List<SocketIOClient> clients = GROUP_CLIENT.get(groupId);
        if (clients != null) {
            clients.remove(client);
            if (clients.isEmpty()) {
                GROUP_CLIENT.remove(groupId);
            }
        }
        log.info("客户端:" + client.getSessionId() + "断开连接");
    }

    @OnEvent(value = "singleChat")
    public void singleChat(SocketIOClient client,AckRequest ackRequest, MessageModel messageModel) {
        log.info("客户端 {} 发送的消息 {}",client.getSessionId(),messageModel.getData());
        //获取目标客户端
        SocketIOClient target= SINGLE_CLIENT.get("");

        if (ackRequest.isAckRequested()) {
            // send ack response with data to client
            ackRequest.sendAckData("client message was delivered to server!", "yeah!");
        }
    }
    @OnEvent(value = "groupChat")
    public void groupChat(SocketIOClient client, MessageModel messageModel) {
        log.info("客户端 {} 发送的消息 {}",client.getSessionId(),messageModel.getData());
        //获取目标群组下的所有客户端
        String groupId = getGroupId(client);
        List<SocketIOClient> targetGroup= GROUP_CLIENT.get(groupId);
    }
    public String getClientId(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        String teamId = client.getHandshakeData().getSingleUrlParam("teamId");
        String userType = client.getHandshakeData().getSingleUrlParam("userType");
        String app = client.getHandshakeData().getSingleUrlParam("app");
        String terminal = client.getHandshakeData().getSingleUrlParam("terminal");
        HttpHeaders headers = client.getHandshakeData().getHttpHeaders();
        headers.get("");
        return app+":"+userId+":"+teamId+":"+userType+":"+terminal;
    }
    public String getGroupId(SocketIOClient client) {
       return client.getHandshakeData().getSingleUrlParam("teamId");
    }
    public List<SocketIOClient> getGroup(SocketIOClient client) {
        String clientId = getClientId(client);
        List<SocketIOClient> clients = GROUP_CLIENT.computeIfAbsent(clientId, k -> new ArrayList<>());
        clients.add(client);
        return clients;
    }
}
