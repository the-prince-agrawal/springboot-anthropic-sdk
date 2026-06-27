package com.prince.anthropic.sdk.service;

import com.prince.anthropic.sdk.client.AnthropicApiClient;
import com.prince.anthropic.sdk.dto.ChatResponse;
import com.prince.anthropic.sdk.enums.SystemPrompt;
import com.prince.anthropic.sdk.enums.Temperature;
import com.prince.anthropic.sdk.models.ChatMessage;
import com.prince.anthropic.sdk.store.ConversationStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnthropicService {

    @Value("${anthropic.system-prompt-id:GENERAL_TEACHER}")
    private String systemPromptId;

    private final AnthropicApiClient anthropicApiClient;
    private final ConversationStore conversationStore;

    public ChatResponse chat(String prompt, Temperature temperature) {
        conversationStore.addUserMessage(prompt);
        String systemPrompt = SystemPrompt.getPrompt(systemPromptId);

        String assistantPrefill = "```json";
        List<String> stopSequences = List.of("```");
        String response = anthropicApiClient.chat(conversationStore.getHistory(), systemPrompt, temperature, assistantPrefill, stopSequences);
        conversationStore.addAssistantMessage(response);
        return new ChatResponse(response);
    }

    public void chatStream(String prompt, Temperature temperature, SseEmitter emitter) {
        conversationStore.addUserMessage(prompt);
        String systemPrompt = SystemPrompt.getPrompt(systemPromptId);
        StringBuilder response = new StringBuilder();
        new Thread(() -> {
            try {
                anthropicApiClient.chatStream(conversationStore.getHistory(), systemPrompt, temperature, chunk -> {
                        response.append(chunk);
                        try {
                            emitter.send(
                                SseEmitter.event()
                                    .name("message")
                                    .data(chunk)
                            );
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }});
                conversationStore.addAssistantMessage(response.toString());
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
    }

    public void clearConversation() {
        conversationStore.clear();
    }

    public List<ChatMessage> getConversation() {
        return conversationStore.getHistory();
    }
}