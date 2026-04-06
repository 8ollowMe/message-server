package com.followMe.message_server.domain.message.client;

import com.followMe.message_server.domain.message.dto.SlackWebhookRequest;
import com.followMe.message_server.domain.message.entity.SlackProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class SlackApiClientImpl implements SlackApiClient {

    private final SlackProperties slackProperties;
    private final RestClient restClient = RestClient.create();

    @Override
    public void sendMessage(String message) {
        SlackWebhookRequest request = new SlackWebhookRequest(message);

        restClient.post()
                .uri(slackProperties.getWebhookUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}
