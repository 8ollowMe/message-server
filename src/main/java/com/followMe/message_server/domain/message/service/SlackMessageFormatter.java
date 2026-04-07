package com.followMe.message_server.domain.message.service;

import com.followMe.message_server.domain.ai.dto.request.DispatchDeadlineProcessRequest;
import com.followMe.message_server.domain.ai.dto.request.ProductItemRequest;
import com.followMe.message_server.domain.ai.dto.request.WaypointRequest;
import com.followMe.message_server.domain.ai.dto.response.DispatchDeadlineResult;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SlackMessageFormatter {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public String formatOrderAlert(
      DispatchDeadlineProcessRequest request, DispatchDeadlineResult result) {
    String products =
        request.getProducts().stream()
            .map(p -> p.getProductName() + " " + p.getQuantity() + p.getUnit())
            .collect(Collectors.joining(", "));

    String waypoints =
        (request.getWaypoints() == null || request.getWaypoints().isEmpty())
            ? "없음"
            : request.getWaypoints().stream()
                .map(WaypointRequest::getName)
                .collect(Collectors.joining(", "));

    return """
                [허브 발송 알림]

                주문 번호 : %s
                주문자 정보 : %s / %s
                주문 시간 : %s
                상품 정보 : %s
                요청 사항 : %s
                발송지 : %s
                경유지 : %s
                도착지 : %s
                배송담당자 : %s / %s

                배송 예상 시간 : %s
                최종 발송 시한 : %s

                요약 : %s
                사유 : %s
                """
        .formatted(
            request.getOrderNumber(),
            request.getOrdererName(),
            request.getOrdererEmail(),
            formatDateTime(request.getOrderTime()),
            products,
            request.getRequestNote(),
            request.getOrigin().getName(),
            waypoints,
            request.getDestination().getAddress(),
            request.getDeliveryManagerName(),
            request.getDeliveryManagerEmail(),
            formatDateTime(result.getEstimatedArrivalAt()),
            formatDateTime(result.getFinalDispatchDeadline()),
            nullToDash(result.getSummary()),
            nullToDash(result.getReason()));
  }

  private String formatDateTime(LocalDateTime value) {
    return value == null ? "-" : value.format(DATE_TIME_FORMATTER);
  }

  private String nullToDash(String value) {
    return (value == null || value.isBlank()) ? "-" : value;
  }

  private String formatProduct(ProductItemRequest item) {
    return item.getProductName() + " " + item.getQuantity() + item.getUnit();
  }
}
