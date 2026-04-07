package com.followMe.message_server.domain.message.entity;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<UUID> {

  @Override
  public Optional<UUID> getCurrentAuditor() {
    return Optional.ofNullable(AuditContextHolder.get());
  }
}
