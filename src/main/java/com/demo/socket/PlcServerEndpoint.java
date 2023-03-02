package com.demo.socket;


import com.demo.plc.IDataSearchService;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
//@ServerEndpoint("/plc/server")
public class PlcServerEndpoint {

    private static final Map<String, WebSocketMessageHandler> CLIENTS_SESSIONS = new ConcurrentHashMap<>();

    private final IDataSearchService dataSearchService;

    public PlcServerEndpoint(IDataSearchService dataSearchService) {
        this.dataSearchService = dataSearchService;
    }

    @OnOpen
    public void onOpen(Session session) {
        CLIENTS_SESSIONS.put(session.getId(), new WebSocketMessageHandler(this.dataSearchService, session));
    }

    @OnClose
    public void onClose(Session session) {
        CLIENTS_SESSIONS.remove(session.getId());
    }

    @OnError
    public void onError(Throwable t) {
        log.error("Error in Server WebSocket: ", t);
    }

    @OnMessage
    public void onMessage(String criteria, Session session) throws EncodeException, IOException {
        CLIENTS_SESSIONS.get(session.getId()).onMessage(criteria);
    }

    public void sendToAll() {
        CLIENTS_SESSIONS.forEach((sessionId, messageHandler) -> messageHandler.sendToClients());
    }

}
