package com.followMe.message_server.global.infra;

import com.followMe.message_server.domain.ai.dto.request.AiHttpRequest;
import com.followMe.message_server.domain.ai.dto.response.AiHttpResponse;
import com.followMe.message_server.domain.notification.dto.request.DispatchDeadlineRequest;
import com.followMe.message_server.domain.notification.dto.response.DispatchDeadlineResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "aiClient", url = "${clients.ai.url}")
public interface AiClient {
  AiHttpResponse requestDispatchDeadline(AiHttpRequest request);

  @PostMapping("/api/internal/v1/ai/dispatch-deadline")
  DispatchDeadlineResponse generateDispatchDeadline(
      @RequestHeader("X-Internal-Request") String internalRequest,
      @RequestHeader("X-System-Id") String systemId,
      @RequestBody DispatchDeadlineRequest request);
}
