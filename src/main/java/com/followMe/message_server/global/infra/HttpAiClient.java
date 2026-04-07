package com.followMe.message_server.global.infra;

import com.followMe.message_server.domain.ai.dto.request.AiHttpRequest;
import com.followMe.message_server.domain.ai.dto.response.AiHttpResponse;
import com.followMe.message_server.domain.ai.entity.AiProperties;
import com.followMe.message_server.global.exception.BusinessException;
import com.followMe.message_server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpAiClient implements AiClient {

    private final WebClient webClient;
    private final AiProperties aiProperties;

    @Override
    public AiHttpResponse requestDispatchDeadline(AiHttpRequest request) {
        try {
            log.info("AI baseUrl={}", aiProperties.getBaseUrl());
            log.info("AI path={}", aiProperties.getPath());

            String response = webClient.post()
                    .uri(aiProperties.getBaseUrl() + aiProperties.getPath())
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + aiProperties.getApiKey())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("AI raw response={}", response);

            return AiHttpResponse.builder()
                    .rawResponse(response)
                    .build();

        } catch (Exception e) {
            log.error("AI 호출 실패", e);
            throw new BusinessException(ErrorCode.AI_CALL_FAILED, e);
        }
    }
}
