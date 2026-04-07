package com.followMe.message_server.domain.ai.api;

import com.followMe.message_server.domain.ai.dto.request.DispatchDeadlineProcessRequest;
import com.followMe.message_server.domain.ai.dto.response.ApiResponse;
import com.followMe.message_server.domain.ai.dto.response.DispatchProcessResponse;
import com.followMe.message_server.domain.ai.service.DispatchDeadlineFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery-plans")
public class DispatchDeadlineController {

  private final DispatchDeadlineFacade dispatchDeadlineFacade;

  @PostMapping
  public ResponseEntity<ApiResponse<DispatchProcessResponse>> process(
      @Valid @RequestBody DispatchDeadlineProcessRequest request) {
    return ResponseEntity.ok(ApiResponse.ok(dispatchDeadlineFacade.process(request)));
  }
}
