package com.prince.anthropic.sdk.client;

import com.anthropic.client.AnthropicClient;
import com.anthropic.core.http.StreamResponse;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.RawContentBlockDelta;
import com.anthropic.models.messages.RawContentBlockDeltaEvent;
import com.anthropic.models.messages.RawMessageStreamEvent;
import com.anthropic.models.messages.TextDelta;
import com.anthropic.models.messages.ThinkingConfigEnabled;
import com.anthropic.models.messages.ThinkingConfigParam;
import com.prince.anthropic.sdk.enums.Role;
import com.prince.anthropic.sdk.enums.Temperature;
import com.prince.anthropic.sdk.models.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnthropicApiClient {

    private final AnthropicClient anthropicClient;

    @Value("${anthropic.model}")
    private String model;

    @Value("${anthropic.max-tokens}")
    private Integer maxTokens;

    @Value("${anthropic.thinking-budget:1024}")
    private Long thinkingBudget;

    @Value("${anthropic.thinking-enabled}")
    private boolean thinkingEnabled;

    public String chat(List<ChatMessage> history,
                       String systemPrompt,
                       Temperature temperature,
                       String assistantPrefill,
                       List<String> stopSequences) {

        log.debug("Chat history: {}, System prompt: {}, Temperature: {}", history, systemPrompt, temperature);

        MessageCreateParams.Builder builder = MessageCreateParams.builder()
            .model(model)
            .maxTokens(maxTokens)
            .system(systemPrompt);

        if(thinkingEnabled){
            ThinkingConfigEnabled thinking = ThinkingConfigEnabled.builder().budgetTokens(thinkingBudget).build();
            builder.thinking(ThinkingConfigParam.ofEnabled(thinking));
        }else{
            // temperature() is deprecated by Anthropic.
            // This is included only for learning/demo purposes.
            builder.temperature(temperature.getValue());
        }


        for (ChatMessage message : history) {
            if (message.getRole() == Role.USER) {
                builder.addUserMessage(message.getContent());
            } else {
                builder.addAssistantMessage(message.getContent());
            }
        }

        //Note: Opus model does not support assistant message prefill
        if (assistantPrefill != null && !model.contains("sonnet")) {
            builder.addAssistantMessage(assistantPrefill);
        }
        if (!CollectionUtils.isEmpty(stopSequences)) {
            builder.stopSequences(stopSequences);
        }

        Message response = anthropicClient.messages().create(builder.build());
        return response.content().stream()
            .filter(block -> block.text().isPresent())
            .findFirst()
            .map(block -> block.text().get().text())
            .orElseThrow(() -> new RuntimeException("No text content in response"));
    }

    public void chatStream(List<ChatMessage> history, String systemPrompt, Temperature temperature, Consumer<String> consumer) {
        MessageCreateParams.Builder builder = MessageCreateParams.builder()
            .model(model)
            .maxTokens(maxTokens)
            .system(systemPrompt)
            .temperature(temperature.getValue());

        for (ChatMessage message : history) {
            if (message.getRole() == Role.USER) {
                builder.addUserMessage(message.getContent());
            } else {
                builder.addAssistantMessage(message.getContent());
            }
        }

        try (StreamResponse<RawMessageStreamEvent> stream = anthropicClient.messages().createStreaming(builder.build())) {
            stream.stream().forEach(event -> {
                if (!event.isContentBlockDelta()) {
                    return;
                }
                RawContentBlockDeltaEvent deltaEvent = event.asContentBlockDelta();
                RawContentBlockDelta delta = deltaEvent.delta();
                if (!delta.isText()) {
                    return;
                }
                TextDelta textDelta = delta.asText();
                consumer.accept(textDelta.text());
            });
        }
    }
}