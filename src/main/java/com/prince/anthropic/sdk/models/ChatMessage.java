package com.prince.anthropic.sdk.models;

import com.prince.anthropic.sdk.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class ChatMessage {
    private Role role;
    private String content;
}
