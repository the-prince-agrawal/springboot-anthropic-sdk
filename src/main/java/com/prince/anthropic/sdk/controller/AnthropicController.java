package com.prince.anthropic.sdk.controller;


import com.prince.anthropic.sdk.dto.ChatRequest;
import com.prince.anthropic.sdk.dto.ChatResponse;
import com.prince.anthropic.sdk.models.ChatMessage;
import com.prince.anthropic.sdk.service.AnthropicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/anthropic")
@RequiredArgsConstructor
public class AnthropicController {

    private final AnthropicService anthropicService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        ChatResponse response = anthropicService.chat(request.getPrompt(), request.getTemperature());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatStream(@Valid @RequestBody ChatRequest request) {
        SseEmitter emitter = new SseEmitter(0L);
        anthropicService.chatStream(
            request.getPrompt(),
            request.getTemperature(),
            emitter);

        return emitter;
    }

    @DeleteMapping("/conversation")
    public ResponseEntity<Void> clearConversation() {
        anthropicService.clearConversation();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<ChatMessage>> getConversation() {
        return ResponseEntity.ok(anthropicService.getConversation());
    }
}
