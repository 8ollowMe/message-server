package com.followMe.message_server.domain.ai.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DispatchProcessResponse {
  private UUID aiHistoryId;
  private UUID slackMessageId;
  private DispatchDeadlineResult dispatchDeadlineResult;
}
