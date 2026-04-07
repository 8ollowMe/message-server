package com.followMe.message_server.domain.message.api;

import com.followMe.message_server.domain.message.dto.SlackMessageSendRequest;
import com.followMe.message_server.domain.message.dto.SlackMessageSendResponse;
import com.followMe.message_server.domain.message.service.SlackMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/messages")
@RequiredArgsConstructor
public class InternalSlackMessageController {

    private final SlackMessageService slackMessageService;

    @PostMapping("/send")
    public ResponseEntity<SlackMessageSendResponse> send(
            @Valid @RequestBody SlackMessageSendRequest request
    ) {
        return ResponseEntity.ok(slackMessageService.send(request));
    }
}