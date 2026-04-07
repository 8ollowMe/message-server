package com.followMe.message_server.domain.message.dto.response;

import com.followMe.message_server.global.enums.SendResult;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlackMessageSendResponse {
  private UUID slackMessageId;
  private SendResult sendResult;
  private String message;
}
