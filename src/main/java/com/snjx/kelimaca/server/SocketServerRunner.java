package com.snjx.kelimaca.server;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.store.pubsub.PubSubStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author by suonanjiexi
 * @version 1.0
 * @apiNote TODO
 * @description TODO
 * @date 2023-02-23 12:20
 */
@Slf4j
@Order(1)
@Component
public class SocketServerRunner implements CommandLineRunner  {

    private final SocketIOServer socketIOServer;

    @Autowired
    public SocketServerRunner(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner() {
        return new SpringAnnotationScanner(socketIOServer);
    }
    @Bean
    public PubSubStore pubSubStore(SocketIOServer socketServer) {
        return socketServer.getConfiguration().getStoreFactory().pubSubStore();
    }
    @Override
    public void run(String... args) {
        // 循环添加命名空间
       /* for(NamespaceEnum namespace : NamespaceEnum.values()){
            socketIOServer.addNamespace("/" + namespace.toString());
        }*/

        socketIOServer.start();
        log.debug("NettySocketIOServer start...");
    }

}
