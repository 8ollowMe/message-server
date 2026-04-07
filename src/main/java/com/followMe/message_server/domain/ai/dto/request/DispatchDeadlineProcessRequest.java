package com.followMe.message_server.domain.ai.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DispatchDeadlineProcessRequest {

    @NotNull
    private UUID orderId;

    @NotNull
    private UUID hubManagerId;

    @NotBlank
    private String orderNumber;

    @NotBlank
    private String ordererName;

    @NotBlank
    private String ordererEmail;

    @NotNull
    private LocalDateTime orderTime;

    @Valid
    @NotNull
    private List<ProductItemRequest> products;

    @NotBlank
    private String requestNote;

    @NotNull
    private LocalDateTime deliveryDueAt;

    @Valid
    @NotNull
    private LocationRequest origin;

    @Valid
    private List<WaypointRequest> waypoints;

    @Valid
    @NotNull
    private LocationRequest destination;

    @NotBlank
    private String deliveryManagerName;

    @NotBlank
    private String deliveryManagerEmail;

    @NotNull
    private LocalTime workStart;

    @NotNull
    private LocalTime workEnd;

    private UUID requestedBy;
}
