package com.prince.anthropic.sdk.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatRequest {

    @NotBlank(message = "Prompt cannot be blank")
    private String prompt;

}