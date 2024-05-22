package com.example.controller;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.model.TransactionData;
import com.example.service.CaptureService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
public class CaptureSocketController {

    private final SocketIOServer server;
    private final CaptureService captureService;
    private final ObservationRegistry observationRegistry;

    public CaptureSocketController(SocketIOServer server, CaptureService captureService, ObservationRegistry observationRegistry) {
        this.server = server;
        this.captureService = captureService;
        this.observationRegistry = observationRegistry;
        server.addEventListener("post_data", TransactionData.class, onData());
    }

    private DataListener<TransactionData> onData() {
        return (senderClient, receivedData, ackSender) -> {
            Observation.createNotStarted("on_data", this.observationRegistry)
                    .observe(() ->
                    {
                        log.info(receivedData.toString());
                        var returnData = captureService.initializeTransaction(receivedData);
                        senderClient.getNamespace().getBroadcastOperations().sendEvent("get_data", returnData);
                    });
        };
    }
    
}