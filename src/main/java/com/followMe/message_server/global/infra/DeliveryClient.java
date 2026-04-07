package com.followMe.message_server.global.infra;

import com.followMe.message_server.domain.notification.dto.DeliveryTargetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "deliveryClient", url = "${clients.delivery.url}")
public interface DeliveryClient {

    @GetMapping("/api/internal/v1/deliveries/morning-targets")
    List<DeliveryTargetDto> getMorningTargets(
            @RequestHeader("X-Internal-Request") String internalRequest,
            @RequestHeader("X-System-Id") String systemId
    );
}
