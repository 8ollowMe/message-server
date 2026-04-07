package com.followMe.message_server.domain.ai.service;

import com.followMe.common.pagination.PageResponse;
import com.followMe.message_server.domain.ai.dto.request.AiHistoryUpdateRequest;
import com.followMe.message_server.domain.ai.dto.response.AiHistoryDetailResponse;
import com.followMe.message_server.domain.ai.dto.response.AiHistorySummaryResponse;
import com.followMe.message_server.domain.ai.entity.AiHistory;
import com.followMe.message_server.domain.ai.repository.AiHistoryRepository;
import com.followMe.message_server.domain.message.entity.AuditContextHolder;
import com.followMe.message_server.global.exception.BusinessException;
import com.followMe.message_server.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.followMe.common.pagination.PageRequest;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AiHistoryServiceImpl implements AiHistoryService {

    private final AiHistoryRepository aiHistoryRepository;

    @Override
    @Transactional
    public AiHistoryDetailResponse get(UUID aiHistoryId) {
        AiHistory history = aiHistoryRepository.findByIdAndDeletedAtIsNull(aiHistoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.AI_HISTORY_NOT_FOUND));

        return AiHistoryDetailResponse.from(history);
    }

    @Override
    @Transactional
    public PageResponse<AiHistorySummaryResponse> getList(PageRequest pageRequest) {
        Page<AiHistory> page = aiHistoryRepository.findAllByDeletedAtIsNull(
                pageRequest.toPageable(Sort.by(Sort.Direction.DESC, "requestedAt"))
        );

        return PageResponse.of(page, AiHistorySummaryResponse::from);
    }

    @Override
    public AiHistoryDetailResponse update(UUID aiHistoryId, AiHistoryUpdateRequest request) {
        UUID auditor = request.getRequestedBy() != null
                ? request.getRequestedBy()
                : UUID.fromString("00000000-0000-0000-0000-000000000000");

        try {
            AuditContextHolder.set(auditor);

            AiHistory history = aiHistoryRepository.findByIdAndDeletedAtIsNull(aiHistoryId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.AI_HISTORY_NOT_FOUND));

            history.updateReason(request.getReason());

            return AiHistoryDetailResponse.from(history);

        } finally {
            AuditContextHolder.clear();
        }
    }

    @Override
    public void delete(UUID aiHistoryId, UUID requestedBy) {
        UUID auditor = requestedBy != null
                ? requestedBy
                : UUID.fromString("00000000-0000-0000-0000-000000000000");

        try {
            AuditContextHolder.set(auditor);

            AiHistory history = aiHistoryRepository.findByIdAndDeletedAtIsNull(aiHistoryId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.AI_HISTORY_NOT_FOUND));

            history.softDelete(auditor);

        } finally {
            AuditContextHolder.clear();
        }
    }
}