package com.followMe.message_server.domain.ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WaypointRequest {

    @NotBlank
    private String name;
}