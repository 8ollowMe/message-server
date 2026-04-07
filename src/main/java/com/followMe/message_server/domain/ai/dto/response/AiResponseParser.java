package com.followMe.message_server.domain.ai.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.followMe.message_server.global.exception.BusinessException;
import com.followMe.message_server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AiResponseParser {

    private final ObjectMapper objectMapper;

    public DispatchDeadlineResult parse(String rawResponse) {
        try {
            JsonNode root = objectMapper.readTree(rawResponse);

            JsonNode textNode = root.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text");

            if (textNode.isMissingNode() || textNode.isNull()) {
                throw new BusinessException(ErrorCode.AI_RESPONSE_PARSE_FAILED);
            }

            String text = textNode.asText();

            String cleaned = stripCodeFence(text);

            JsonNode resultNode = objectMapper.readTree(cleaned);

            String estimatedArrivalAt = resultNode.path("estimatedArrivalAt").asText(null);
            String finalDispatchDeadline = resultNode.path("finalDispatchDeadline").asText(null);
            String summary = resultNode.path("summary").asText(null);
            String reason = resultNode.path("reason").asText(null);

            if (estimatedArrivalAt == null || finalDispatchDeadline == null || summary == null || reason == null) {
                throw new BusinessException(ErrorCode.AI_RESPONSE_PARSE_FAILED);
            }

            return DispatchDeadlineResult.builder()
                    .estimatedArrivalAt(LocalDateTime.parse(estimatedArrivalAt))
                    .finalDispatchDeadline(LocalDateTime.parse(finalDispatchDeadline))
                    .summary(summary)
                    .reason(reason)
                    .build();

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.AI_RESPONSE_PARSE_FAILED, e);
        }
    }

    private String stripCodeFence(String text) {
        String trimmed = text.trim();

        if (trimmed.startsWith("```json")) {
            trimmed = trimmed.substring(7).trim();
        } else if (trimmed.startsWith("```")) {
            trimmed = trimmed.substring(3).trim();
        }

        if (trimmed.endsWith("```")) {
            trimmed = trimmed.substring(0, trimmed.length() - 3).trim();
        }

        return trimmed;
    }
}
