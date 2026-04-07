package com.followMe.message_server.domain.notification.service;

import java.util.UUID;

public interface SlackSender {
    void send(UUID userId, String message);
}