package com.followMe.message_server.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    AI_CALL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "AI_001", "AI 호출에 실패했습니다."),
    AI_RESPONSE_PARSE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "AI_002", "AI 응답 파싱에 실패했습니다."),
    SLACK_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "SLACK_001", "슬랙 메시지 전송에 실패했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_001", "잘못된 요청입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}