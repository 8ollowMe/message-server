package com.followMe.message_server.domain.message.config;

import com.followMe.message_server.domain.message.entity.SlackProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SlackProperties.class)
public class SlackConfig {
}
