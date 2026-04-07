package com.followMe.message_server.domain.notification.service;

import com.followMe.message_server.domain.message.dto.request.SlackMessageSendRequest;
import com.followMe.message_server.domain.message.service.SlackMessageService;
import com.followMe.message_server.global.enums.MessageType;
import com.followMe.message_server.global.enums.ReferenceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlackSenderImpl implements SlackSender {
    private final SlackMessageService slackMessageService;


    @Override
    public void send(UUID userId, String message) {
        SlackMessageSendRequest request = SlackMessageSendRequest.builder()
                .messageType(MessageType.MORNING_ALERT)
                .userId(userId)
                .referenceType(ReferenceType.DELIVERY)
                .referenceId(UUID.randomUUID())
                .message(message)
                .requestedBy(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .build();

        slackMessageService.send(request);
    }
}
