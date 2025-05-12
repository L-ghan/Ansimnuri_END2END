package com.end2end.ansimnuri.chatbot.controller;


import com.end2end.ansimnuri.chatbot.dto.PoliceDto;
import com.end2end.ansimnuri.chatbot.service.ChatService;
import com.end2end.ansimnuri.map.service.PoliceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
public class ChatController {

    @Autowired
    private ChatService chatServ;
    @Autowired
    private PoliceService policeServ;

    @PostMapping("/api")
    public ResponseEntity<String> askLLM(@RequestBody Map<String, Object> request) {
        List<Map<String, String>> messages = (List<Map<String, String>>) request.get("messages");
        String result = chatServ.askOpenAI(messages);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/police")
    public ResponseEntity<Map<String, String>> getPoliceInfo(@RequestParam String location) {
        PoliceDto dto = chatServ.findPoliceByLocation(location);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "name", "관할 경찰서를 찾을 수 없습니다",
                    "address", "입력하신 지역 정보를 다시 확인해주세요"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "name", dto.getName(),
                "address", dto.getAddress()
        ));
    }
}

