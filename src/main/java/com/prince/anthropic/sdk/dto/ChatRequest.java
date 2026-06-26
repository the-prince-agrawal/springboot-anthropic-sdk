package com.prince.anthropic.sdk.dto;

import com.prince.anthropic.sdk.enums.Temperature;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatRequest {

    @NotBlank(message = "Prompt cannot be blank")
    private String prompt;

    private Temperature temperature = Temperature.BALANCED;

}