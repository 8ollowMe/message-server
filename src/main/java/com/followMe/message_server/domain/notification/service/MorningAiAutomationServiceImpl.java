package com.followMe.message_server.domain.notification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.followMe.message_server.domain.ai.entity.AiHistory;
import com.followMe.message_server.domain.ai.repository.AiHistoryRepository;
import com.followMe.message_server.domain.message.entity.SlackMessage;
import com.followMe.message_server.domain.message.repository.SlackMessageRepository;
import com.followMe.message_server.domain.notification.dto.DeliveryTargetDto;
import com.followMe.message_server.domain.notification.dto.request.DispatchDeadlineRequest;
import com.followMe.message_server.domain.notification.dto.response.DispatchDeadlineResponse;
import com.followMe.message_server.global.enums.AiRequestType;
import com.followMe.message_server.global.enums.MessageType;
import com.followMe.message_server.global.enums.ReferenceType;
import com.followMe.message_server.global.infra.AiClient;
import com.followMe.message_server.global.infra.DeliveryClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MorningAiAutomationServiceImpl implements MorningAiAutomationService {

    private final DeliveryClient deliveryClient;
    private final AiClient aiClient;
    private final SlackSender slackSender; // 실제 슬랙 API 호출 담당
    private final SlackMessageRepository slackMessageRepository;
    private final AiHistoryRepository aiHistoryRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void execute() {
        List<DeliveryTargetDto> targets =
                deliveryClient.getMorningTargets("true", "MESSAGE-SERVER");

        for (DeliveryTargetDto target : targets) {
            processTarget(target);
        }
    }

    private void processTarget(DeliveryTargetDto target) {

        AiHistory aiHistory = null;
        SlackMessage slackMessage = null;

        try {
            DispatchDeadlineRequest aiRequest = DispatchDeadlineRequest.builder()
                    .deliveryId(target.getDeliveryId())
                    .orderId(target.getOrderId())
                    .departureHubId(target.getDepartureHubId())
                    .destinationAddress(target.getDestinationAddress())
                    .requestedArrivalTime(target.getRequestedArrivalTime())
                    .build();

            String requestJson = objectMapper.writeValueAsString(aiRequest);

            aiHistory = AiHistory.create(
                    AiRequestType.DISPATCH_DEADLINE,
                    ReferenceType.DELIVERY,
                    target.getDeliveryId(),
                    requestJson
            );
            aiHistoryRepository.save(aiHistory);

            DispatchDeadlineResponse aiResponse =
                    aiClient.generateDispatchDeadline("true", "MESSAGE-SERVER", aiRequest);

            String responsePayload = objectMapper.writeValueAsString(aiResponse);

            aiHistory.markSuccess(responsePayload, aiResponse.getReason());

            String message = buildMessage(target, aiResponse);

            slackMessage = SlackMessage.create(
                    MessageType.MORNING_ALERT,
                    target.getUserId(),
                    ReferenceType.DELIVERY,
                    target.getDeliveryId(),
                    message
            );

            slackMessageRepository.save(slackMessage);

            slackSender.send(target.getUserId(), message);

            slackMessage.markSuccess();

        } catch (Exception e) {

            log.error("자동 발송 실패", e);

            if (aiHistory != null) {
                aiHistory.markFailed(null, e.getMessage());
            }

            if (slackMessage == null) {
                slackMessage = SlackMessage.create(
                        MessageType.MORNING_ALERT,
                        target.getUserId(),
                        ReferenceType.DELIVERY,
                        target.getDeliveryId(),
                        "자동 발송 실패"
                );
                slackMessageRepository.save(slackMessage);
            }

            slackMessage.markFail();
        }
    }

    private String buildMessage(DeliveryTargetDto target,
                                DispatchDeadlineResponse res) {

        return """
                [배송 안내]
                
                주문번호: %s
                요청 도착 시간: %s
                
                예상 도착: %s
                발송 시각: %s
                
                요약: %s
                사유: %s
                """.formatted(
                target.getOrderNumber(),
                target.getRequestedArrivalTime(),
                res.getEstimatedArrivalAt(),
                res.getFinalDispatchDeadline(),
                res.getSummary(),
                res.getReason()
        );
    }
}
