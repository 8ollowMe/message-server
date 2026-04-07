package com.followMe.message_server.domain.ai.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DispatchProcessResponse {
    private UUID aiHistoryId;
    private UUID slackMessageId;
    private DispatchDeadlineResult dispatchDeadlineResult;
}
