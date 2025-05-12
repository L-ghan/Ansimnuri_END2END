package com.end2end.ansimnuri.note.endpoint;

import com.end2end.ansimnuri.util.config.WebSocketConfig;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/note", configurator = WebSocketConfig.class)
public class NoteEndpoint {
    private static final Map<String, Object> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(String userId) {
        clients.put(userId, userId);
    }
}
