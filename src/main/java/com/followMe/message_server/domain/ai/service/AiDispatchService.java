package com.followMe.message_server.domain.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.followMe.message_server.domain.ai.dto.request.AiHttpRequest;
import com.followMe.message_server.domain.ai.dto.request.DispatchDeadlineProcessRequest;
import com.followMe.message_server.domain.ai.dto.response.AiHttpResponse;
import com.followMe.message_server.domain.ai.dto.response.AiResponseParser;
import com.followMe.message_server.domain.ai.dto.response.DispatchDeadlineResult;
import com.followMe.message_server.domain.ai.entity.AiHistory;
import com.followMe.message_server.domain.ai.repository.AiHistoryRepository;
import com.followMe.message_server.domain.message.entity.AuditContextHolder;
import com.followMe.message_server.global.enums.AiRequestType;
import com.followMe.message_server.global.enums.AiStatus;
import com.followMe.message_server.global.enums.ReferenceType;
import com.followMe.message_server.global.exception.BusinessException;
import com.followMe.message_server.global.exception.ErrorCode;
import com.followMe.message_server.global.infra.AiClient;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AiDispatchService {

    private final PromptGenerator promptGenerator;
    private final AiClient aiClient;
    private final AiResponseParser aiResponseParser;
    private final AiHistoryRepository aiHistoryRepository;
    private final ObjectMapper objectMapper;

    public AiDispatchResult calculateAndSave(DispatchDeadlineProcessRequest request) {
        UUID auditor = request.getRequestedBy() != null
                ? request.getRequestedBy()
                : UUID.fromString("00000000-0000-0000-0000-000000000000");

        try {
            AuditContextHolder.set(auditor);

            String requestPayload = toJson(request);
            String prompt = promptGenerator.generate(request);

            AiHistory history = AiHistory.builder()
                    .id(UUID.randomUUID())
                    .requestType(AiRequestType.DISPATCH_DEADLINE)
                    .referenceType(ReferenceType.ORDER)
                    .referenceId(request.getOrderId())
                    .requestPayload(requestPayload)
                    .responsePayload("")
                    .status(AiStatus.PENDING)
                    .requestedAt(LocalDateTime.now())
                    .reason(null)
                    .build();

            aiHistoryRepository.save(history);

            try {
                AiHttpResponse rawResponse = aiClient.requestDispatchDeadline(
                        AiHttpRequest.builder()
                                .prompt(prompt)
                                .build()
                );

                DispatchDeadlineResult result = aiResponseParser.parse(rawResponse.getRawResponse());

                history.markSuccess(rawResponse.getRawResponse(), result.getReason());

                return AiDispatchResult.builder()
                        .aiHistory(history)
                        .dispatchDeadlineResult(result)
                        .build();

            } catch (Exception e) {
                history.markFailed("", e.getMessage());
                throw e;
            }

        } finally {
            AuditContextHolder.clear();
        }
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패", e);
        }
    }

    @Getter
    @Builder
    public static class AiDispatchResult {
        private AiHistory aiHistory;
        private DispatchDeadlineResult dispatchDeadlineResult;
    }
}
