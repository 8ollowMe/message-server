package com.followMe.message_server.domain.ai.dto;

import com.followMe.message_server.domain.ai.dto.request.AiHttpRequest;
import com.followMe.message_server.domain.ai.dto.request.GeminiRequest;

import java.util.List;

public class GeminiRequestMapper {

    public static GeminiRequest from(AiHttpRequest request) {
        return GeminiRequest.builder()
                .contents(List.of(
                        GeminiRequest.Content.builder()
                                .parts(List.of(
                                        GeminiRequest.Part.builder()
                                                .text(request.getPrompt())
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }
}
