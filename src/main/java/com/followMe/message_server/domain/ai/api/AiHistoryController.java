package com.followMe.message_server.domain.ai.api;

import com.followMe.common.pagination.PageRequest;
import com.followMe.common.pagination.PageResponse;
import com.followMe.message_server.domain.ai.dto.request.AiHistoryUpdateRequest;
import com.followMe.message_server.domain.ai.dto.response.AiHistoryDetailResponse;
import com.followMe.message_server.domain.ai.dto.response.AiHistorySummaryResponse;
import com.followMe.message_server.domain.ai.dto.response.ApiResponse;
import com.followMe.message_server.domain.ai.service.AiHistoryService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai/histories")
@RequiredArgsConstructor
public class AiHistoryController {

  private final AiHistoryService aiHistoryService;

  @GetMapping("/{aiHistoryId}")
  public ResponseEntity<ApiResponse<AiHistoryDetailResponse>> get(@PathVariable UUID aiHistoryId) {
    return ResponseEntity.ok(ApiResponse.ok(aiHistoryService.get(aiHistoryId)));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<AiHistorySummaryResponse>>> getList(
      PageRequest pageRequest) {
    return ResponseEntity.ok(ApiResponse.ok(aiHistoryService.getList(pageRequest)));
  }

  @PatchMapping("/{aiHistoryId}")
  public ResponseEntity<ApiResponse<AiHistoryDetailResponse>> update(
      @PathVariable UUID aiHistoryId, @Valid @RequestBody AiHistoryUpdateRequest request) {
    return ResponseEntity.ok(ApiResponse.ok(aiHistoryService.update(aiHistoryId, request)));
  }

  @DeleteMapping("/{aiHistoryId}")
  public ResponseEntity<ApiResponse<Void>> delete(
      @PathVariable UUID aiHistoryId, @RequestParam(required = false) UUID requestedBy) {
    aiHistoryService.delete(aiHistoryId, requestedBy);
    return ResponseEntity.ok(ApiResponse.ok(null));
  }
}
