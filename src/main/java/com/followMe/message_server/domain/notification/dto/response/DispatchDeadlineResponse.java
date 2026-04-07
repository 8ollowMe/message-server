package com.followMe.message_server.domain.notification.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DispatchDeadlineResponse {
  private LocalDateTime estimatedArrivalAt;
  private LocalDateTime finalDispatchDeadline;
  private String summary;
  private String reason;
}
