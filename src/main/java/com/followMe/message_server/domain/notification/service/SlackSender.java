package com.followMe.message_server.domain.notification.service;

import com.followMe.message_server.global.enums.ReferenceType;

import java.util.UUID;

public interface SlackSender {
    public void send(UUID userId, String message);
}