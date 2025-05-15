package com.end2end.ansimnuri.chatbot.controller;

import com.end2end.ansimnuri.chatbot.dto.PoliceDto;
import com.end2end.ansimnuri.chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/chatBot")
@RestController
public class ChatController {

    @Autowired
    private ChatService chatServ;

    @PostMapping("/api")
    public ResponseEntity<String> askLLM(@RequestBody Map<String, Object> request) {
        List<Map<String, String>> messages = (List<Map<String, String>>) request.get("messages");
        String result = chatServ.askOpenAI(messages);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/police")
    public ResponseEntity<List<Map<String, String>>> findPoliceByLocation(@RequestParam String keyword) {
        List<PoliceDto> dtoList = chatServ.findPoliceByLocation(keyword);

        if (dtoList == null || dtoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(List.of(Map.of("name", "결과 없음", "address", "해당 지역의 경찰서를 찾을 수 없습니다.")));
        }

        List<Map<String, String>> result = new ArrayList<>();
        for (PoliceDto p : dtoList) {
            result.add(Map.of(
                    "name", p.getName(),
                    "address", p.getAddress()
            ));
        }

        return ResponseEntity.ok(result);
    }
}

