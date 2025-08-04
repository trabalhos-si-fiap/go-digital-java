package com.laroz.services;


import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class Langchain4jChatService {

    @Value("${spring.ai.host}")
    private String OLLAMA_HOST;
    @Value("${spring.ai.ollama.chat.model}")
    private String MODEL;

    private ChatModel connectModel() {
        return OllamaChatModel.builder()
                .baseUrl(OLLAMA_HOST)
                .modelName(MODEL)
                .logRequests(true)
                .build();
    }

    public String run(String userPrompt) {
        var model = connectModel();
        return  model.chat(userPrompt);
    }

}