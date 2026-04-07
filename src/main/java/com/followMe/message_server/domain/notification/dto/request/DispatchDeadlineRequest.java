package com.followMe.message_server.domain.notification.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class DispatchDeadlineRequest {
    private UUID deliveryId;
    private UUID orderId;
    private UUID departureHubId;
    private String destinationAddress;
    private LocalDateTime requestedArrivalTime;
}
