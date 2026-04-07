package com.followMe.message_server.domain.notification.service;

import com.followMe.message_server.domain.message.service.SlackMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlackSenderImpl implements SlackSender {
    private final SlackMessageService slackMessageService;

    @Override
    public void send(UUID userId, String message) {
        slackMessageService.sendToUser(userId, message);
    }
}
