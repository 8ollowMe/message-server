package com.followMe.message_server.domain.message.service;

import com.followMe.message_server.domain.message.client.SlackApiClient;
import com.followMe.message_server.domain.message.dto.SlackMessageSendRequest;
import com.followMe.message_server.domain.message.dto.SlackMessageSendResponse;
import com.followMe.message_server.domain.message.entity.SlackMessage;
import com.followMe.message_server.domain.message.repository.SlackMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SlackMessageServiceImpl implements SlackMessageService {

    private final SlackMessageRepository slackMessageRepository;
    private final SlackApiClient slackApiClient;

    @Override
    public SlackMessageSendResponse send(SlackMessageSendRequest request) {
        UUID systemUser = UUID.fromString("00000000-0000-0000-0000-000000000000");

        SlackMessage slackMessage = SlackMessage.create(
                request.getMessageType(),
                request.getUserId(),
                request.getReferenceType(),
                request.getReferenceId(),
                request.getMessage(),
                systemUser
        );

        slackMessageRepository.save(slackMessage);

        try {
            slackApiClient.sendMessage(request.getMessage());
            slackMessage.markSuccess();

            return SlackMessageSendResponse.builder()
                    .slackMessageId(slackMessage.getId())
                    .sendResult(slackMessage.getSendResult())
                    .message("슬랙 메시지 전송 성공")
                    .build();

        } catch (Exception e) {
            slackMessage.markFail(systemUser);

            return SlackMessageSendResponse.builder()
                    .slackMessageId(slackMessage.getId())
                    .sendResult(slackMessage.getSendResult())
                    .message("슬랙 메시지 전송 실패: " + e.getMessage())
                    .build();
        }
    }
}
