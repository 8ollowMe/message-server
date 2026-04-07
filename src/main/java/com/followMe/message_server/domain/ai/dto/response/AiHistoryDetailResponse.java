package com.followMe.message_server.domain.ai.dto.response;

import com.followMe.message_server.domain.ai.entity.AiHistory;
import com.followMe.message_server.global.enums.AiRequestType;
import com.followMe.message_server.global.enums.AiStatus;
import com.followMe.message_server.global.enums.ReferenceType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiHistoryDetailResponse {

  private UUID aiHistoryId;
  private AiRequestType requestType;
  private ReferenceType referenceType;
  private UUID referenceId;
  private String requestPayload;
  private String responsePayload;
  private AiStatus status;
  private LocalDateTime requestedAt;
  private String reason;
  private LocalDateTime createdAt;
  private UUID createdBy;
  private LocalDateTime updatedAt;
  private UUID updatedBy;

  public static AiHistoryDetailResponse from(AiHistory history) {
    return AiHistoryDetailResponse.builder()
        .aiHistoryId(history.getId())
        .requestType(history.getRequestType())
        .referenceType(history.getReferenceType())
        .referenceId(history.getReferenceId())
        .requestPayload(history.getRequestPayload())
        .responsePayload(history.getResponsePayload())
        .status(history.getStatus())
        .requestedAt(history.getRequestedAt())
        .reason(history.getReason())
        .createdAt(LocalDateTime.ofInstant(history.getCreatedAt(), ZoneId.of("Asia/Seoul")))
        .createdBy(history.getCreatedBy())
        .updatedAt(LocalDateTime.ofInstant(history.getUpdatedAt(), ZoneId.of("Asia/Seoul")))
        .updatedBy(history.getUpdatedBy())
        .build();
  }
}
