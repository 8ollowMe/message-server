package com.followMe.message_server.domain.message.service;

import com.followMe.message_server.domain.ai.dto.request.DispatchDeadlineProcessRequest;
import com.followMe.message_server.domain.ai.dto.response.DispatchDeadlineResult;
import com.followMe.message_server.domain.message.dto.SlackMessageSendRequest;
import com.followMe.message_server.domain.message.dto.SlackMessageSendResponse;
import com.followMe.message_server.domain.message.entity.SlackMessage;

import java.util.UUID;

public interface SlackMessageService {
    SlackMessageSendResponse send(SlackMessageSendRequest request);

    SlackMessage sendOrderAlert(
            DispatchDeadlineProcessRequest request,
            DispatchDeadlineResult result,
            UUID referenceId,
            UUID userId,
            UUID requestedBy
    );
}
