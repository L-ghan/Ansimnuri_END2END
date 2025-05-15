package com.end2end.ansimnuri.chatbot.controller;

import com.end2end.ansimnuri.chatbot.service.ChatService;
import com.end2end.ansimnuri.map.dto.PoliceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
        List<PoliceDTO> dto = chatServ.findPoliceByLocation(keyword);

        List<Map<String, String>> result = new ArrayList<>();
        if (dto == null) {
            result.add(Map.of(
                    "name", "관할 경찰서를 찾을 수 없습니다",
                    "address", "입력하신 지역 정보를 다시 확인해주세요"
            ));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        for (PoliceDTO p : dto) {
            Map<String, String> map = new HashMap<>();
            map.put("name", p.getName());
            map.put("address", p.getAddress());
            result.add(map);
        }

        return ResponseEntity.ok(result);
    }

}

