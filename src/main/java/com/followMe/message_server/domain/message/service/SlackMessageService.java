package com.followMe.message_server.domain.message.service;

import com.followMe.message_server.domain.message.dto.SlackMessageSendRequest;
import com.followMe.message_server.domain.message.dto.SlackMessageSendResponse;

public interface SlackMessageService {
    SlackMessageSendResponse send(SlackMessageSendRequest request);
}
