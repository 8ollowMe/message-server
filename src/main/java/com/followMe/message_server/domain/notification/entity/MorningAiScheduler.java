package com.followMe.message_server.domain.notification.entity;

import com.followMe.message_server.domain.notification.service.MorningAiAutomationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MorningAiScheduler {

    private final MorningAiAutomationService morningAiAutomationService;

    @Scheduled(cron = "0 0 6 * * *", zone = "Asia/Seoul")
    public void run() {
        log.info("=== 아침 AI 자동 발송 시작 ===");
        morningAiAutomationService.execute();
        log.info("=== 아침 AI 자동 발송 종료 ===");
    }
}
