package com.followMe.message_server.domain.ai.service;

import com.followMe.common.pagination.PageResponse;
import com.followMe.message_server.domain.ai.dto.request.AiHistoryUpdateRequest;
import com.followMe.message_server.domain.ai.dto.response.AiHistoryDetailResponse;
import com.followMe.message_server.domain.ai.dto.response.AiHistorySummaryResponse;
import com.followMe.common.pagination.PageRequest;

import java.util.UUID;


public interface AiHistoryService {

    AiHistoryDetailResponse get(UUID aiHistoryId);

    PageResponse<AiHistorySummaryResponse> getList(PageRequest pageRequest);

    AiHistoryDetailResponse update(UUID aiHistoryId, AiHistoryUpdateRequest request);

    void delete(UUID aiHistoryId, UUID requestedBy);
}