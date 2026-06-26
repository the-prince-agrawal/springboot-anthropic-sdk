package com.prince.anthropic.sdk.client;

import com.anthropic.client.AnthropicClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.prince.anthropic.sdk.enums.Role;
import com.prince.anthropic.sdk.models.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class AnthropicApiClient {

    private final AnthropicClient anthropicClient;

    @Value("${anthropic.model}")
    private String model;

    @Value("${anthropic.max-tokens}")
    private Integer maxTokens;

    public String chat(List<ChatMessage> history) {
        log.info("Chat history: {}", history);
        MessageCreateParams.Builder builder = MessageCreateParams.builder()
            .model(model)
            .maxTokens(maxTokens);

        for (ChatMessage message : history) {
            if (message.getRole() == Role.USER) {
                builder.addUserMessage(message.getContent());
            } else {
                builder.addAssistantMessage(message.getContent());
            }
        }

        Message response = anthropicClient.messages().create(builder.build());
        return response.content().get(0).text().get().text();
    }
}