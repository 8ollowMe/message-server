package com.followMe.message_server.domain.message.dto;

import com.followMe.message_server.domain.message.enums.SendResult;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SlackMessageSendResponse {
    private UUID slackMessageId;
    private SendResult sendResult;
    private String message;
}
