package com.followMe.message_server.domain.message.client;

public interface SlackApiClient {
    void sendMessage(String message);
}
