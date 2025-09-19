package com.techify.anomalydetection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.chat.client.ChatClient;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel) {
        // Spring Boot auto-creates OpenAiChatModel using your properties
        return ChatClient.builder(openAiChatModel).build();
    }
}
