package com.followMe.message_server.domain.ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationRequest {

  @NotBlank private String name;

  @NotBlank private String address;
}
