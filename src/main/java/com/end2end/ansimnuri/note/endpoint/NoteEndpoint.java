package com.end2end.ansimnuri.note.endpoint;

import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.member.service.MemberService;
import com.end2end.ansimnuri.note.service.NoteService;
import com.end2end.ansimnuri.util.JWTUtil;
import com.end2end.ansimnuri.util.provider.SpringProvider;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ws/note/{token}")
public class NoteEndpoint {
    private JWTUtil jwtUtil = SpringProvider.Spring.getBean(JWTUtil.class);
    private MemberService memberService = SpringProvider.Spring.getBean(MemberService.class);
    private NoteService noteService = SpringProvider.Spring.getBean(NoteService.class);

    private static final Map<Session, MemberDTO> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        MemberDTO memberDTO = memberService
                .selectByLoginId(jwtUtil.getLoginId(token));
        clients.put(session, memberDTO);
    }

    @OnMessage
    public void onMessage(String message) {
        clients.forEach((session, memberDTO) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
    }
}
