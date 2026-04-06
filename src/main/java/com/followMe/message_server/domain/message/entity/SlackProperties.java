package com.followMe.message_server.domain.message.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "slack")
public class SlackProperties {
    private String webhookUrl;
}
