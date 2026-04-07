package com.followMe.message_server.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "external.slack")
public class SlackProperties {
    private String webhookUrl;
}
