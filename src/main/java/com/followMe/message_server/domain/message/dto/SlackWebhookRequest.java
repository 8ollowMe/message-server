package com.followMe.message_server.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SlackWebhookRequest {
    private String text;
}
