package com.followMe.message_server.domain.ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AiHistoryUpdateRequest {

  @NotBlank private String reason;

  private UUID requestedBy;
}
