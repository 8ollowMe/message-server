package com.followMe.message_server.domain.notification.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeliveryTargetDto {
  private UUID deliveryId;
  private UUID orderId;
  private UUID departureHubId;
  private UUID userId;
  private String orderNumber;
  private String destinationAddress;
  private LocalDateTime requestedArrivalTime;
}
