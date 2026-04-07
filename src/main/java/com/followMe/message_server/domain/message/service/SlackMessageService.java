package com.followMe.message_server.domain.message.service;

import com.followMe.common.pagination.PageRequest;
import com.followMe.common.pagination.PageResponse;
import com.followMe.message_server.domain.ai.dto.request.DispatchDeadlineProcessRequest;
import com.followMe.message_server.domain.ai.dto.response.DispatchDeadlineResult;
import com.followMe.message_server.domain.message.dto.request.SlackMessageSendRequest;
import com.followMe.message_server.domain.message.dto.request.SlackMessageUpdateRequest;
import com.followMe.message_server.domain.message.dto.response.SlackMessageDetailResponse;
import com.followMe.message_server.domain.message.dto.response.SlackMessageSendResponse;
import com.followMe.message_server.domain.message.dto.response.SlackMessageSummaryResponse;
import com.followMe.message_server.domain.message.entity.SlackMessage;
import java.util.UUID;

public interface SlackMessageService {
  SlackMessageSendResponse send(SlackMessageSendRequest request);

  SlackMessage sendOrderAlert(
      DispatchDeadlineProcessRequest request,
      DispatchDeadlineResult result,
      UUID referenceId,
      UUID userId,
      UUID requestedBy);

  SlackMessageDetailResponse get(UUID slackMessageId);

  PageResponse<SlackMessageSummaryResponse> getList(PageRequest pageRequest);

  SlackMessageDetailResponse update(UUID slackMessageId, SlackMessageUpdateRequest request);

  void delete(UUID slackMessageId, UUID requestedBy);
}
