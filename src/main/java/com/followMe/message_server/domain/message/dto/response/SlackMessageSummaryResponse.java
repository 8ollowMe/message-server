package com.followMe.message_server.domain.message.dto.response;

import com.followMe.message_server.domain.message.entity.SlackMessage;
import com.followMe.message_server.global.enums.MessageType;
import com.followMe.message_server.global.enums.ReferenceType;
import com.followMe.message_server.global.enums.SendResult;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlackMessageSummaryResponse {

  private UUID slackMessageId;
  private MessageType messageType;
  private UUID userId;
  private ReferenceType referenceType;
  private UUID referenceId;
  private String message;
  private SendResult sendResult;
  private LocalDateTime sendAt;
  private LocalDateTime createdAt;

  public static SlackMessageSummaryResponse from(SlackMessage slackMessage) {
    return SlackMessageSummaryResponse.builder()
        .slackMessageId(slackMessage.getId())
        .messageType(slackMessage.getMessageType())
        .userId(slackMessage.getUserId())
        .referenceType(slackMessage.getReferenceType())
        .referenceId(slackMessage.getReferenceId())
        .message(slackMessage.getMessage())
        .sendResult(slackMessage.getSendResult())
        .sendAt(slackMessage.getSendAt())
        .createdAt(LocalDateTime.ofInstant(slackMessage.getCreatedAt(), ZoneId.of("Asia/Seoul")))
        .build();
  }
}
