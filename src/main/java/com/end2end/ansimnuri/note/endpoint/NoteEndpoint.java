package com.end2end.ansimnuri.note.endpoint;

import com.end2end.ansimnuri.member.dto.MemberDTO;
import com.end2end.ansimnuri.member.service.MemberService;
import com.end2end.ansimnuri.note.dto.NoteSocketDTO;
import com.end2end.ansimnuri.util.JWTUtil;
import com.end2end.ansimnuri.util.provider.SpringProvider;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ws/note/{token}")
public class NoteEndpoint {
    private final JWTUtil jwtUtil = SpringProvider.Spring.getBean(JWTUtil.class);
    private final MemberService memberService = SpringProvider.Spring.getBean(MemberService.class);

    private static final Map<Session, MemberDTO> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        if(jwtUtil.validation(token)) {
            MemberDTO memberDTO = memberService
                    .selectByLoginId(jwtUtil.getLoginId(token));
            clients.put(session, memberDTO);
        } else {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
    }

    public static void send(NoteSocketDTO dto) {
        clients.forEach((session, memberDTO) -> {
            try {
                session.getBasicRemote().sendText(dto.toString());
            } catch (Exception e) {
                throw new RuntimeException("소켓 통신 중 에러가 발생했습니다.", e);
            }
        });
    }
}
