package com.followMe.message_server.domain.ai.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "external.ai")
public class AiProperties {
    private String baseUrl;
    private String path;
    private String apiKey;
}
