package com.followMe.message_server.domain.message.api;

import com.followMe.common.pagination.PageResponse;
import com.followMe.message_server.domain.ai.dto.response.ApiResponse;
import com.followMe.message_server.domain.message.dto.request.SlackMessageSendRequest;
import com.followMe.message_server.domain.message.dto.request.SlackMessageUpdateRequest;
import com.followMe.message_server.domain.message.dto.response.SlackMessageDetailResponse;
import com.followMe.message_server.domain.message.dto.response.SlackMessageSendResponse;
import com.followMe.message_server.domain.message.dto.response.SlackMessageSummaryResponse;
import com.followMe.message_server.domain.message.service.SlackMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.followMe.common.pagination.PageRequest;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class SlackMessageController {

    private final SlackMessageService slackMessageService;

    @PostMapping("/send")
    public ResponseEntity<SlackMessageSendResponse> send(
            @Valid @RequestBody SlackMessageSendRequest request
    ) {
        return ResponseEntity.ok(slackMessageService.send(request));
    }
    @GetMapping("/{slackMessageId}")
    public ResponseEntity<ApiResponse<SlackMessageDetailResponse>> get(
            @PathVariable UUID slackMessageId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(slackMessageService.get(slackMessageId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<SlackMessageSummaryResponse>>> getList(
            PageRequest pageRequest
    ) {
        return ResponseEntity.ok(ApiResponse.ok(slackMessageService.getList(pageRequest)));
    }

    @PutMapping("/{slackMessageId}")
    public ResponseEntity<ApiResponse<SlackMessageDetailResponse>> update(
            @PathVariable UUID slackMessageId,
            @Valid @RequestBody SlackMessageUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(slackMessageService.update(slackMessageId, request)));
    }

    @DeleteMapping("/{slackMessageId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable UUID slackMessageId,
            @RequestParam(required = false) UUID requestedBy
    ) {
        slackMessageService.delete(slackMessageId, requestedBy);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}