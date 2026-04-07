package com.followMe.message_server.global.infra;

import com.followMe.message_server.domain.ai.dto.request.AiHttpRequest;
import com.followMe.message_server.domain.ai.dto.response.AiHttpResponse;

public interface AiClient {
    AiHttpResponse requestDispatchDeadline(AiHttpRequest request);
}
