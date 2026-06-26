package com.prince.anthropic.sdk.service;

import com.prince.anthropic.sdk.client.AnthropicApiClient;
import com.prince.anthropic.sdk.dto.ChatResponse;
import com.prince.anthropic.sdk.enums.SystemPrompt;
import com.prince.anthropic.sdk.models.ChatMessage;
import com.prince.anthropic.sdk.store.ConversationStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnthropicService {

    @Value("${anthropic.system-prompt-id:GENERAL_TEACHER}")
    private String systemPromptId;

    private final AnthropicApiClient anthropicApiClient;
    private final ConversationStore conversationStore;

    public ChatResponse chat(String prompt) {
        conversationStore.addUserMessage(prompt);
        String systemPrompt = SystemPrompt.getPrompt(systemPromptId);
        String response = anthropicApiClient.chat(conversationStore.getHistory(), systemPrompt);
        conversationStore.addAssistantMessage(response);
        return new ChatResponse(response);
    }

    public void clearConversation() {
        conversationStore.clear();
    }

    public List<ChatMessage> getConversation() {
        return conversationStore.getHistory();
    }
}