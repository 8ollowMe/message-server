package com.followMe.message_server.domain.message.repository;

import com.followMe.message_server.domain.message.entity.SlackMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlackMessageRepository extends JpaRepository<SlackMessage, UUID> {
}
