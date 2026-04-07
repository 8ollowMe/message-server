package com.followMe.message_server.domain.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SlackMessageUpdateRequest {

  @NotBlank private String message;

  private UUID requestedBy;
}
