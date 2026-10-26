package com.cetys.rag_ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    private static final String SYSTEM_PROMP = """
            Eres un chatbot que me ayuda a resolver dudas para donar sangre, ayudar a los usuarios
            """;

    @Bean
    ChatClient chatClient(ChatModel chatModel, ChatMemory chatMemory){
        return ChatClient
                .builder(chatModel)
                .defaultSystem(SYSTEM_PROMP)
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(chatMemory).build())
                .build();
    }
}
