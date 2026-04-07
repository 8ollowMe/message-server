package com.followMe.message_server.domain.ai.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DispatchDeadlineResult {

  private LocalDateTime estimatedArrivalAt;
  private LocalDateTime finalDispatchDeadline;
  private String summary;
  private String reason;
}
