package com.followMe.message_server.domain.notification.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DispatchDeadlineResponse {
    private LocalDateTime estimatedArrivalAt;
    private LocalDateTime finalDispatchDeadline;
    private String summary;
    private String reason;
}