package com.followMe.message_server.domain.ai.dto.request;

import java.util.List;

public class GeminiRequestMapper {

  public static GeminiRequest from(AiHttpRequest request) {
    return GeminiRequest.builder()
        .contents(
            List.of(
                GeminiRequest.Content.builder()
                    .parts(List.of(GeminiRequest.Part.builder().text(request.getPrompt()).build()))
                    .build()))
        .build();
  }
}
