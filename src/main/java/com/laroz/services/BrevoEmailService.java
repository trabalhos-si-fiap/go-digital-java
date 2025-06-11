package com.laroz.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laroz.dtos.mail.EmailMessage;
import com.laroz.interfaces.EmailSender;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

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

        System.out.println(jsonBody);

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


        return response;
    }
}
