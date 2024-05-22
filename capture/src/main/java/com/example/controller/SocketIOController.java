package com.example.controller;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.model.Message;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SocketIOController {

    private final SocketIOServer server;

    public SocketIOController(SocketIOServer server) {
        this.server = server;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onData());
    }

    @Observed
    private DataListener<Message> onData() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            senderClient.getNamespace().getBroadcastOperations().sendEvent("get_message_for_".concat(data.acquirerId()), data);
        };
    }

    private ConnectListener onConnected() {
        return (client) -> {
            log.info("Client session ID={} - connected to socket controller", client.getSessionId().toString());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client session ID={} - disconnected from socket controller", client.getSessionId().toString());
        };
    }

}