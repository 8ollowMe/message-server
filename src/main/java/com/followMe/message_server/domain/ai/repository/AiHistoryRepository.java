package com.followMe.message_server.domain.ai.repository;

import com.followMe.message_server.domain.ai.entity.AiHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AiHistoryRepository extends JpaRepository<AiHistory, UUID> {
}
