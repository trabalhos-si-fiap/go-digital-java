package com.laros.services;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.chat.ChatModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class Langchain4jChatService {
    private final ChatModel chatModel;
    public Langchain4jChatService(
            @Value("${spring.ai.gemini.key}") String apiKey,
            @Value("${spring.ai.gemini.chat.model}") String model
    ) {
        this.chatModel = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(model)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    public String run(String userPrompt) {
        return chatModel.chat(userPrompt);
    }

}