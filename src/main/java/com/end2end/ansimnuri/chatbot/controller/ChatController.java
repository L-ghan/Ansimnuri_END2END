package com.end2end.ansimnuri.chatbot.controller;

import com.end2end.ansimnuri.chatbot.dto.PoliceDTO;
import com.end2end.ansimnuri.chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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
            return ResponseEntity.ok(Collections.emptyList());
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
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/support")
    public ResponseEntity<String> getSupportAnswer(@RequestParam String question) {
        String answer = chatServ.findSupportAnswer(question);
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/faq")
    public ResponseEntity<String> getFAQAnswer(@RequestParam String question) {
        String answer = chatServ.findFAQAnswer(question);
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/news/top3")
    public List<Map<String, String>> getTop3News() {
        return chatServ.getTop3News();
    }

    @PostMapping("/news/summarize")
    public List<Map<String, String>> summarizeTop3News(@RequestBody List<Map<String, String>> newsList) {
        return chatServ.summarizeNews(newsList);
    }


}

