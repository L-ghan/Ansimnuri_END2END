package com.end2end.ansimnuri.note.endpoint;

import com.end2end.ansimnuri.note.dto.NoteSocketDTO;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NoteEndpoint extends TextWebSocketHandler {
    private static final Set<WebSocketSession> clients = ConcurrentHashMap.newKeySet();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 메시지 처리 로직 구현
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 웹소켓 연결이 수립되면 호출되는 메서드
        clients.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 웹소켓 연결이 종료되면 호출되는 메서드
        clients.remove(session);
    }

    public static void send(NoteSocketDTO dto) {
    }
}
