package com.followMe.message_server.domain.message.dto;

import com.followMe.message_server.domain.message.enums.MessageType;
import com.followMe.message_server.domain.message.enums.ReferenceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SlackMessageSendRequest {

    @NotNull
    private MessageType messageType;

    @NotNull
    private UUID userId;

    @NotNull
    private ReferenceType referenceType;

    @NotNull
    private UUID referenceId;

    @NotBlank
    private String message;
}
