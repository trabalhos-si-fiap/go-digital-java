package com.laros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.laros.services.Langchain4jChatService;

import java.util.Map;

@RestController
public class LangChain4JController {

    @Autowired
    private Langchain4jChatService chatService;

    @GetMapping("/ai/basic/langchain4j/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Mostre uma frase de alguém famoso") String message) {
        return Map.of("data", chatService.run(message));
    }
}
