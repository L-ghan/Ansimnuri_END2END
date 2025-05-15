package com.end2end.ansimnuri.chatbot.controller;

import com.end2end.ansimnuri.chatbot.dto.PoliceDTO;
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
        List<PoliceDTO> dto = chatServ.findPoliceByLocation(keyword);

        if (dto == null || dto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(List.of(Map.of("name", "결과 없음", "address", "해당 지역의 경찰서를 찾을 수 없습니다.")));
        }

        List<Map<String, String>> result = new ArrayList<>();
        for (PoliceDTO p : dto) {
            result.add(Map.of(
                    "name", p.getName(),
                    "address", p.getAddress()
            ));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/guide")
    public ResponseEntity<String> getGuideAnswer(@RequestParam String question) {
        String answer = chatServ.findGuideAnswer(question);
        return ResponseEntity.ok(answer != null ? answer : "해당 질문에 대한 대처 요령이 없습니다.");
    }

    @GetMapping("/support")
    public ResponseEntity<String> getSupportAnswer(@RequestParam String question) {
        String answer = chatServ.findSupportAnswer(question);
        return ResponseEntity.ok(answer != null ? answer : "해당 제도에 대한 정보가 없습니다.");
    }



}

