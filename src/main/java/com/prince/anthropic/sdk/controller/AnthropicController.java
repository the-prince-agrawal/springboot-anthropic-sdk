package com.prince.anthropic.sdk.controller;


import com.prince.anthropic.sdk.dto.ChatRequest;
import com.prince.anthropic.sdk.dto.ChatResponse;
import com.prince.anthropic.sdk.service.AnthropicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anthropic")
@RequiredArgsConstructor
public class AnthropicController {

    private final AnthropicService anthropicService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        ChatResponse response = anthropicService.chat(request.getPrompt());
        return ResponseEntity.ok(response);
    }
}
