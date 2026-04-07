package com.followMe.message_server.domain.ai.service;

import com.followMe.message_server.domain.ai.dto.request.DispatchDeadlineProcessRequest;
import com.followMe.message_server.domain.ai.dto.response.DispatchProcessResponse;
import com.followMe.message_server.domain.message.service.SlackMessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispatchDeadlineFacade {

    private final AiDispatchService aiDispatchService;
    private final SlackMessageService slackMessageService;

    @Transactional
    public DispatchProcessResponse process(DispatchDeadlineProcessRequest request) {
        AiDispatchService.AiDispatchResult aiResult = aiDispatchService.calculateAndSave(request);

        var slackMessage = slackMessageService.sendOrderAlert(
                request,
                aiResult.getDispatchDeadlineResult(),
                request.getOrderId(),
                request.getHubManagerId(),
                request.getRequestedBy()
        );

        return DispatchProcessResponse.builder()
                .aiHistoryId(aiResult.getAiHistory().getId())
                .slackMessageId(slackMessage.getId())
                .dispatchDeadlineResult(aiResult.getDispatchDeadlineResult())
                .build();
    }
}