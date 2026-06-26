package com.prince.anthropic.sdk.store;

import com.prince.anthropic.sdk.enums.Role;
import com.prince.anthropic.sdk.models.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConversationStore {

    private final List<ChatMessage> history = new ArrayList<>();

    public void addUserMessage(String content) {
        history.add(new ChatMessage(Role.USER, content));
    }

    public void addAssistantMessage(String content) {
        history.add(new ChatMessage(Role.ASSISTANT, content));
    }

    public List<ChatMessage> getHistory() {
        return List.copyOf(history);
    }

    public void clear() {
        history.clear();
    }
}