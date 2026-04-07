package com.followMe.message_server.domain.message.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SlackMessageUpdateRequest {

    @NotBlank
    private String message;

    private UUID requestedBy;
}