package com.followMe.message_server.global.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.followMe.message_server.domain.ai.dto.request.AiHttpRequest;
import com.followMe.message_server.domain.ai.dto.request.GeminiRequest;
import com.followMe.message_server.domain.ai.dto.request.GeminiRequestMapper;
import com.followMe.message_server.domain.ai.dto.response.AiHttpResponse;
import com.followMe.message_server.global.properties.AiProperties;
import com.followMe.message_server.global.exception.BusinessException;
import com.followMe.message_server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpAiClient implements AiClient {

    private final WebClient webClient;
    private final AiProperties aiProperties;
    private final ObjectMapper objectMapper;

    @Override
    public AiHttpResponse requestDispatchDeadline(AiHttpRequest request) {
        try {
            String url = aiProperties.getBaseUrl() + aiProperties.getPath();

            GeminiRequest geminiRequest = GeminiRequestMapper.from(request);

            log.info("AI request url={}", url);
            log.info("AI request body={}", objectMapper.writeValueAsString(geminiRequest));

            String response = webClient.post()
                    .uri(url)
                    .header("x-goog-api-key", aiProperties.getApiKey())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(geminiRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("AI raw response={}", response);

            return AiHttpResponse.builder()
                    .rawResponse(response)
                    .build();

        } catch (WebClientResponseException e) {
            log.error("AI 호출 실패 status={}, body={}",
                    e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new BusinessException(ErrorCode.AI_CALL_FAILED, e);
        } catch (Exception e) {
            log.error("AI 호출 실패", e);
            throw new BusinessException(ErrorCode.AI_CALL_FAILED, e);
        }
    }
}
