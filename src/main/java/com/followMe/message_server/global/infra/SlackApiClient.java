package com.followMe.message_server.global.infra;

public interface SlackApiClient {
  void sendMessage(String message);
}
