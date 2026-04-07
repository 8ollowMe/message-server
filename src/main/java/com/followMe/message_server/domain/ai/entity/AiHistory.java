package com.followMe.message_server.domain.ai.entity;

import com.followMe.common.entity.BaseAudit;
import com.followMe.message_server.global.enums.AiRequestType;
import com.followMe.message_server.global.enums.AiStatus;
import com.followMe.message_server.global.enums.ReferenceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_ai_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AiHistory extends BaseAudit {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type", nullable = false, length = 50)
    private AiRequestType requestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", nullable = false, length = 50)
    private ReferenceType referenceType;

    @Column(name = "reference_id", nullable = false)
    private UUID referenceId;

    @Lob
    @Column(name = "request_payload", nullable = false)
    private String requestPayload;

    @Lob
    @Column(name = "response_payload", nullable = false)
    private String responsePayload;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private AiStatus status;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    @Lob
    @Column(name = "reason")
    private String reason;

    public static AiHistory create(
            AiRequestType requestType,
            ReferenceType referenceType,
            UUID referenceId,
            String requestPayload
    ) {
        return AiHistory.builder()
                .id(UUID.randomUUID())
                .requestType(requestType)
                .referenceType(referenceType)
                .referenceId(referenceId)
                .requestPayload(requestPayload)
                .status(AiStatus.PENDING)
                .requestedAt(LocalDateTime.now())
                .build();
    }

    public void markSuccess(String responsePayload, String reason) {
        this.responsePayload = responsePayload;
        this.reason = reason;
        this.status = AiStatus.SUCCESS;
        this.respondedAt = LocalDateTime.now();
    }

    public void markFailed(String responsePayload, String reason) {
        this.responsePayload = responsePayload;
        this.reason = reason;
        this.status = AiStatus.FAILED;
        this.respondedAt = LocalDateTime.now();
    }

    public void updateReason(String reason) {
        this.reason = reason;
    }

    public void aiSoftDelete(UUID deletedBy) {
        this.softDelete(deletedBy);
    }
}
