package com.followMe.message_server.domain.message.entity;

import com.followMe.common.entity.BaseAudit;
import com.followMe.message_server.global.enums.MessageType;
import com.followMe.message_server.global.enums.ReferenceType;
import com.followMe.message_server.global.enums.SendResult;
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
public class SlackMessage extends BaseAudit {

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

    public static SlackMessage create(
            MessageType messageType,
            UUID userId,
            ReferenceType referenceType,
            UUID referenceId,
            String message
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
                .build();
    }

    public void markSuccess() {
        this.sendResult = SendResult.SUCCESS;
    }

    public void markFail() {
        this.sendResult = SendResult.FAIL;
        this.retryCount = this.retryCount == null ? 1 : this.retryCount + 1;
    }
    public void updateMessage(String message) {
        this.message = message;
    }

    public void softDeleteMessage(UUID deletedBy) {
        this.softDelete(deletedBy);
    }
}

