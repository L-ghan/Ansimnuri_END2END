package com.end2end.ansimnuri.note.endpoint;

import com.end2end.ansimnuri.note.dto.NoteSocketDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NoteEndpoint extends TextWebSocketHandler {
    private static final Set<WebSocketSession> clients = ConcurrentHashMap.newKeySet();
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        clients.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        clients.remove(session);
    }

    public static void send(NoteSocketDTO dto) {
        System.out.println("메세지: " + dto.getRequesterId());
        String jsonMessage;
        try {
            jsonMessage = objectMapper.writeValueAsString(dto);
        } catch (IOException e) {
            throw new RuntimeException("메시지 변환 실패", e);
        }
        TextMessage message = new TextMessage(jsonMessage);

        clients.forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
