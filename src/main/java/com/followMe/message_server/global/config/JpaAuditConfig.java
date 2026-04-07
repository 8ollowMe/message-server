package com.followMe.message_server.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfig {

    private static final UUID SYSTEM_USER =
            UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return () -> Optional.of(SYSTEM_USER);
    }
}