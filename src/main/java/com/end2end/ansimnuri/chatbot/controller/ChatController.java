package com.end2end.ansimnuri.chatbot.controller;

import com.end2end.ansimnuri.chatbot.dto.PoliceDTO;
import com.end2end.ansimnuri.chatbot.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Tag(name="챗봇 API", description = "챗봇 기능에 관련한 API")
@RequiredArgsConstructor
@RequestMapping("/chatBot")
@RestController
public class ChatController {
    private final ChatService chatServ;

    @Operation(summary = "llm에게 대답을 받는 api", description = "질문 내용을 openai api를 통해 대답을 받아 출력한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "500", description = "api 연동 오류입니다.")
    })
    @PostMapping("/api")
    public ResponseEntity<String> askLLM(@RequestBody Map<String, Object> request) {
        List<Map<String, String>> messages = (List<Map<String, String>>) request.get("messages");
        String result = chatServ.askOpenAI(messages);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "경찰서 검색 api", description = "검색된 키워드의 위치의 경찰서 리스트를 조회한다.")
    @ApiResponse(responseCode = "200", description = "정상 작동입니다.")
    @GetMapping("/police")
    public ResponseEntity<List<Map<String, String>>> findPoliceByLocation(
            @Parameter(description = "검색어")
            @RequestParam String keyword) {
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

    @Operation(summary = "가이드 조회 api", description = "요청된 질문에 대한 가이드 답변을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 질문입니다.")
    })
    @GetMapping("/guide")
    public ResponseEntity<String> getGuideAnswer(
            @Parameter(description = "질문")
            @RequestParam String question) {
        String answer = chatServ.findGuideAnswer(question);
        return ResponseEntity.ok(answer);
    }

    @Operation(summary = "지원 조회 api", description = "요청된 질문에 대한 지원 답변을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 질문입니다.")
    })
    @GetMapping("/support")
    public ResponseEntity<String> getSupportAnswer(
            @Parameter(description = "질문")
            @RequestParam String question) {
        String answer = chatServ.findSupportAnswer(question);
        return ResponseEntity.ok(answer);
    }

    @Operation(summary = "자주 묻는 질문 조회 api", description = "요청된 질문에 대한 자주 묻는 질문 답변을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 질문입니다.")
    })
    @GetMapping("/faq")
    public ResponseEntity<String> getFAQAnswer(
            @Parameter(description = "질문")
            @RequestParam String question) {
        String answer = chatServ.findFAQAnswer(question);
        return ResponseEntity.ok(answer);
    }

    @Operation(summary = "최신 뉴스 3개 조회 api", description = "데이터 베이스 안에 저장된 최근 신문 내용 3개를 조회한다.")
    @ApiResponse(responseCode = "200", description = "정상 작동입니다.")
    @GetMapping("/news/top3")
    public List<Map<String, String>> getTop3News() {
        return chatServ.getTop3News();
    }

    @Operation(summary = "최신 뉴스 3개 요약 조회 api", description = "데이터 베이스 안에 저장된 최근 신문 3개를 llm으로 요약해 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터 입니다."),
            @ApiResponse(responseCode = "500", description = "api 연결 오류입니다.")
    })
    @PostMapping("/news/summarize")
    public List<Map<String, String>> summarizeTop3News(@RequestBody List<Map<String, String>> newsList) {
        return chatServ.summarizeNews(newsList);
    }


}

