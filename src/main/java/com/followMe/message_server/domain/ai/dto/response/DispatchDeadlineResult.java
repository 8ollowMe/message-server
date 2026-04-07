package com.followMe.message_server.domain.ai.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DispatchDeadlineResult {

    private LocalDateTime estimatedArrivalAt;
    private LocalDateTime finalDispatchDeadline;
    private String summary;
    private String reason;
}