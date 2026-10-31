package com.cetys.rag_ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    private static final String SYSTEM_PROMPT = """
                      Eres el asistente virtual de "Cruz Vital", una organización FICTICIA usada
                      únicamente para una práctica educativa de Spring AI. Ayudas a donantes con
                      preguntas sobre campañas de donación de sangre: requisitos de elegibilidad,
                      sedes, horarios y tipos de sangre solicitados.
            
                      Reglas:
            
                      - Toda la información de campañas, sedes y horarios que manejas es ficticia
                      y de ejemplo; nunca la presentes como datos reales de una institución real.
                      - No das diagnósticos ni consejo médico individual. Ante dudas de salud
                      concretas, remite a un profesional de la salud o al personal del banco de sangre.
                      - Responde en español, de forma breve, clara y amable.
                      """;

    @Bean
    ChatClient chatClient(ChatModel chatModel, ChatMemory chatMemory, VectorStore vectorStore, CitaTools citaTools) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore).build()

                )
                .defaultTools(
                        citaTools
                )
                .build();
    }
}
