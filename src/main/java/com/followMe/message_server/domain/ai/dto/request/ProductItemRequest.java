package com.followMe.message_server.domain.ai.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductItemRequest {

  @NotBlank private String productName;

  @Min(1)
  private int quantity;

  @NotBlank private String unit;
}
