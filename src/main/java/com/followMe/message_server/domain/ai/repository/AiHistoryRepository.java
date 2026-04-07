package com.followMe.message_server.domain.ai.repository;

import com.followMe.message_server.domain.ai.entity.AiHistory;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiHistoryRepository extends JpaRepository<AiHistory, UUID> {

  Optional<AiHistory> findByIdAndDeletedAtIsNull(UUID id);

  Page<AiHistory> findAllByDeletedAtIsNull(Pageable pageable);
}
