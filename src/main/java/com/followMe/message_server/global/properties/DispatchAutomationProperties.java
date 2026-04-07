package com.followMe.message_server.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "automation.dispatch")
public class DispatchAutomationProperties {

  private String cron;
  private String zone;
  private boolean enabled;
  private String senderSystemId;
}
