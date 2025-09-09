package com.laros.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laros.dtos.mail.EmailMessage;
import com.laros.interfaces.EmailSender;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class BrevoEmailService implements EmailSender {

    @Value("${BREVO_API_KEY}")
    private String brevoApiKey;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public HttpResponse<String> sendEmail(EmailMessage message) throws IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(message);

        log.debug("[BREVO] Json Body: " + jsonBody);

        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("api-key", brevoApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =  HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        log.debug("[BREVO] HTTP Response: " + response.statusCode());

        if (response.statusCode() != 201) {
            log.error(response.body());
        }
        return response;
    }
}
