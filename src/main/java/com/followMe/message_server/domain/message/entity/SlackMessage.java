package com.followMe.message_server.domain.message.entity;

import com.followMe.message_server.domain.message.enums.MessageType;
import com.followMe.message_server.domain.message.enums.ReferenceType;
import com.followMe.message_server.domain.message.enums.SendResult;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_slack_message")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SlackMessage {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false, length = 50)
    private MessageType messageType;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", nullable = false, length = 50)
    private ReferenceType referenceType;

    @Column(name = "reference_id", nullable = false)
    private UUID referenceId;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "send_at", nullable = false)
    private LocalDateTime sendAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "send_result", nullable = false, length = 50)
    private SendResult sendResult;

    @Column(name = "retry_count")
    private Integer retryCount;

    // common-lib 시 삭제
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    public static SlackMessage create(
            MessageType messageType,
            UUID userId,
            ReferenceType referenceType,
            UUID referenceId,
            String message,
            UUID createdBy
    ) {
        LocalDateTime now = LocalDateTime.now();

        return SlackMessage.builder()
                .id(UUID.randomUUID())
                .messageType(messageType)
                .userId(userId)
                .referenceType(referenceType)
                .referenceId(referenceId)
                .message(message)
                .sendAt(now)
                .sendResult(SendResult.PENDING)
                .retryCount(0)
                .createdAt(now)
                .createdBy(createdBy)
                .build();
    }

    public void markSuccess() {
        this.sendResult = SendResult.SUCCESS;
        this.updatedAt = LocalDateTime.now();
    }

    public void markFail(UUID updatedBy) {
        this.sendResult = SendResult.FAIL;
        this.retryCount = this.retryCount == null ? 1 : this.retryCount + 1;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }
}

