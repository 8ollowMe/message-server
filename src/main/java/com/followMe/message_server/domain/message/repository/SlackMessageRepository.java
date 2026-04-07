package com.followMe.message_server.domain.message.repository;

import com.followMe.message_server.domain.message.entity.SlackMessage;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SlackMessageRepository
    extends JpaRepository<SlackMessage, UUID>, JpaSpecificationExecutor<SlackMessage> {

  Optional<SlackMessage> findByIdAndDeletedAtIsNull(UUID id);

  Page<SlackMessage> findAllByDeletedAtIsNull(Pageable pageable);
}
