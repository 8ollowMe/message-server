package com.followMe.message_server.domain.message.dto.request;

import com.followMe.message_server.global.enums.MessageType;
import com.followMe.message_server.global.enums.ReferenceType;
import com.followMe.message_server.global.enums.SendResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SlackMessageSearchRequest {

    private MessageType messageType;
    private SendResult sendResult;
    private UUID userId;
    private ReferenceType referenceType;
    private UUID referenceId;
}