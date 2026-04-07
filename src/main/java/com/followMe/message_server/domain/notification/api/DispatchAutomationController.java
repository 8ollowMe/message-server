package com.followMe.message_server.domain.notification.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/automation/dispatch")
@RequiredArgsConstructor
public class DispatchAutomationController {

    private final DispatchAiAutomationService dispatchAiAutomationService;

    // 수동 테스트용
    @PostMapping("/run")
    public ResponseEntity<String> runNow() {
        dispatchAiAutomationService.executeMorningDispatch();
        return ResponseEntity.ok("아침 배송 자동 발송 작업이 실행되었습니다.");
    }
}