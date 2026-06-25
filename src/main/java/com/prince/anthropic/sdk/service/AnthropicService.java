package com.prince.anthropic.sdk.service;

import com.prince.anthropic.sdk.client.AnthropicApiClient;
import com.prince.anthropic.sdk.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnthropicService {

    private final AnthropicApiClient anthropicApiClient;

    public ChatResponse chat(String prompt) {
        String response = anthropicApiClient.chat(prompt);
        return new ChatResponse(response);
    }
}