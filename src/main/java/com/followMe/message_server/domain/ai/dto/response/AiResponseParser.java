package com.followMe.message_server.domain.ai.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.followMe.message_server.global.exception.BusinessException;
import com.followMe.message_server.global.exception.ErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiResponseParser {

  private final ObjectMapper objectMapper;

  public DispatchDeadlineResult parse(String rawResponse) {
    try {
      JsonNode root = objectMapper.readTree(rawResponse);

      String estimatedArrivalAt = root.get("estimatedArrivalAt").asText();
      String finalDispatchDeadline = root.get("finalDispatchDeadline").asText();
      String summary = root.get("summary").asText();
      String reason = root.get("reason").asText();

      return DispatchDeadlineResult.builder()
          .estimatedArrivalAt(LocalDateTime.parse(estimatedArrivalAt))
          .finalDispatchDeadline(LocalDateTime.parse(finalDispatchDeadline))
          .summary(summary)
          .reason(reason)
          .build();
    } catch (Exception e) {
      throw new BusinessException(ErrorCode.AI_RESPONSE_PARSE_FAILED, e);
    }
  }
}
