package com.followMe.message_server.domain.message.service;

import com.followMe.common.pagination.PageResponse;
import com.followMe.message_server.domain.ai.dto.request.DispatchDeadlineProcessRequest;
import com.followMe.message_server.domain.ai.dto.response.DispatchDeadlineResult;
import com.followMe.message_server.domain.message.dto.*;
import com.followMe.message_server.global.enums.MessageType;
import com.followMe.message_server.global.enums.ReferenceType;
import com.followMe.message_server.global.exception.BusinessException;
import com.followMe.message_server.global.exception.ErrorCode;
import com.followMe.message_server.global.infra.SlackApiClient;
import com.followMe.message_server.domain.message.entity.AuditContextHolder;
import com.followMe.message_server.domain.message.entity.SlackMessage;
import com.followMe.message_server.domain.message.repository.SlackMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import com.followMe.common.pagination.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SlackMessageServiceImpl implements SlackMessageService {

    private final SlackMessageRepository slackMessageRepository;
    private final SlackApiClient slackApiClient;
    private final SlackMessageFormatter slackMessageFormatter;

    @Override
    @Transactional
    public SlackMessageSendResponse send(SlackMessageSendRequest request) {
        UUID auditor = request.getRequestedBy() != null
                ? request.getRequestedBy()
                : UUID.fromString("00000000-0000-0000-0000-000000000000");

        try {
            AuditContextHolder.set(auditor);

            SlackMessage slackMessage = SlackMessage.create(
                    request.getMessageType(),
                    request.getUserId(),
                    request.getReferenceType(),
                    request.getReferenceId(),
                    request.getMessage()
            );


            try {
                slackApiClient.sendMessage(request.getMessage());
                slackMessage.markSuccess();
                slackMessageRepository.saveAndFlush(slackMessage);
                return SlackMessageSendResponse.builder()
                        .slackMessageId(slackMessage.getId())
                        .sendResult(slackMessage.getSendResult())
                        .message("슬랙 메시지 전송 성공")
                        .build();

            } catch (Exception e) {
                slackMessage.markFail();
                slackMessageRepository.saveAndFlush(slackMessage);
                return SlackMessageSendResponse.builder()
                        .slackMessageId(slackMessage.getId())
                        .sendResult(slackMessage.getSendResult())
                        .message("슬랙 메시지 전송 실패: " + e.getMessage())
                        .build();

            }

        } finally {

            AuditContextHolder.clear();
        }
    }
    @Override
    public SlackMessage sendOrderAlert(
            DispatchDeadlineProcessRequest request,
            DispatchDeadlineResult result,
            UUID referenceId,
            UUID userId,
            UUID requestedBy
    ) {
        UUID auditor = requestedBy != null
                ? requestedBy
                : UUID.fromString("00000000-0000-0000-0000-000000000000");

        try {
            AuditContextHolder.set(auditor);

            String formattedMessage = slackMessageFormatter.formatOrderAlert(request, result);

            SlackMessage slackMessage = SlackMessage.create(
                    MessageType.ORDER_ALERT,
                    userId,
                    ReferenceType.ORDER,
                    referenceId,
                    formattedMessage
            );

            slackMessageRepository.save(slackMessage);

            try {
                slackApiClient.sendMessage(formattedMessage);
                slackMessage.markSuccess();
                return slackMessage;
            } catch (Exception e) {
                slackMessage.markFail();
                throw e;
            }

        } finally {
            AuditContextHolder.clear();
        }
    }
    @Override
    @Transactional
    public SlackMessageDetailResponse get(UUID slackMessageId) {
        SlackMessage slackMessage = slackMessageRepository.findByIdAndDeletedAtIsNull(slackMessageId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SLACK_MESSAGE_NOT_FOUND));

        return SlackMessageDetailResponse.from(slackMessage);
    }

    @Override
    @Transactional
    public PageResponse<SlackMessageSummaryResponse> getList(PageRequest pageRequest) {
        Page<SlackMessage> page = slackMessageRepository.findAllByDeletedAtIsNull(
                pageRequest.toPageable(Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        return PageResponse.of(page, SlackMessageSummaryResponse::from);
    }

    @Override
    public SlackMessageDetailResponse update(UUID slackMessageId, SlackMessageUpdateRequest request) {
        UUID auditor = request.getRequestedBy() != null
                ? request.getRequestedBy()
                : UUID.fromString("00000000-0000-0000-0000-000000000000");

        try {
            AuditContextHolder.set(auditor);

            SlackMessage slackMessage = slackMessageRepository.findByIdAndDeletedAtIsNull(slackMessageId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.SLACK_MESSAGE_NOT_FOUND));

            slackMessage.updateMessage(request.getMessage());

            return SlackMessageDetailResponse.from(slackMessage);

        } finally {
            AuditContextHolder.clear();
        }
    }

    @Override
    public void delete(UUID slackMessageId, UUID requestedBy) {
        UUID auditor = requestedBy != null
                ? requestedBy
                : UUID.fromString("00000000-0000-0000-0000-000000000000");

        try {
            AuditContextHolder.set(auditor);

            SlackMessage slackMessage = slackMessageRepository.findByIdAndDeletedAtIsNull(slackMessageId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.SLACK_MESSAGE_NOT_FOUND));

            slackMessage.softDelete(auditor);

        } finally {
            AuditContextHolder.clear();
        }
    }

}
