package com.followMe.message_server.domain.ai.service;

import com.followMe.message_server.domain.ai.dto.request.DispatchDeadlineProcessRequest;
import com.followMe.message_server.domain.ai.dto.request.ProductItemRequest;
import com.followMe.message_server.domain.ai.dto.request.WaypointRequest;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PromptGenerator {

  public String generate(DispatchDeadlineProcessRequest request) {
    String products =
        request.getProducts().stream().map(this::formatProduct).collect(Collectors.joining(", "));

    String waypoints =
        request.getWaypoints() == null || request.getWaypoints().isEmpty()
            ? "없음"
            : request.getWaypoints().stream()
                .map(WaypointRequest::getName)
                .collect(Collectors.joining(", "));

    return """
                당신은 물류 발송 시한 및 배송 예상 시간 계산 AI입니다.
                아래 정보를 바탕으로,
                1) 배송 예상 도착 시간
                2) 납기 시간 내 도착하기 위한 최종 발송 시한
                을 계산하세요.

                [주문 정보]
                주문번호: %s
                주문자: %s / %s
                주문시간: %s
                상품정보: %s
                요청사항: %s
                납기일시: %s

                [배송 정보]
                발송지: %s
                경유지: %s
                도착지: %s
                배송담당자: %s / %s
                배송담당자 근무시간: %s ~ %s

                아래를 모두 고려하세요.
                - 상품 및 수량
                - 납기 요청 시간
                - 발송지, 경유지, 도착지
                - 배송담당자 근무시간
                - 허브 이동 및 상차/하차에 필요한 일반적인 물류 처리 시간

                응답은 반드시 아래 JSON 형식만 반환하세요.
                반드시 순수 JSON만 반환하세요.
                마크다운 코드블록(```)은 절대 포함하지 마세요.
                {
                  "estimatedArrivalAt": "2025-12-12T13:30:00",
                  "finalDispatchDeadline": "2025-12-10T09:00:00",
                  "summary": "2025-12-12 13:30 도착 예상이며, 납기 준수를 위해 2025-12-10 09:00까지 발송이 필요합니다.",
                  "reason": "경유 허브 이동 시간, 상품 수량, 납기 요청 시각, 배송담당자 근무시간을 고려한 결과입니다."
                }
                """
        .formatted(
            request.getOrderNumber(),
            request.getOrdererName(),
            request.getOrdererEmail(),
            request.getOrderTime(),
            products,
            request.getRequestNote(),
            request.getDeliveryDueAt(),
            request.getOrigin().getName(),
            waypoints,
            request.getDestination().getAddress(),
            request.getDeliveryManagerName(),
            request.getDeliveryManagerEmail(),
            request.getWorkStart(),
            request.getWorkEnd());
  }

  private String formatProduct(ProductItemRequest item) {
    return item.getProductName() + " " + item.getQuantity() + item.getUnit();
  }
}
