package com.followMe.message_server.domain.ai.dto.response;

import com.followMe.message_server.domain.ai.entity.AiHistory;
import com.followMe.message_server.global.enums.AiRequestType;
import com.followMe.message_server.global.enums.AiStatus;
import com.followMe.message_server.global.enums.ReferenceType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiHistorySummaryResponse {

  private UUID aiHistoryId;
  private AiRequestType requestType;
  private ReferenceType referenceType;
  private UUID referenceId;
  private AiStatus status;
  private LocalDateTime requestedAt;
  private String reason;

  public static AiHistorySummaryResponse from(AiHistory history) {
    return AiHistorySummaryResponse.builder()
        .aiHistoryId(history.getId())
        .requestType(history.getRequestType())
        .referenceType(history.getReferenceType())
        .referenceId(history.getReferenceId())
        .status(history.getStatus())
        .requestedAt(history.getRequestedAt())
        .reason(history.getReason())
        .build();
  }
}
