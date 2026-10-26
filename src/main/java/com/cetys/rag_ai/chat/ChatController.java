package com.cetys.rag_ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    record ChatRequest(String conversationId, String message){}
    record ChatResponse(String reply){}

    @PostMapping("/chat")
    ChatResponse chat(@RequestBody ChatRequest request){
        var reply =
                chatClient
                        .prompt()
                        .user(request.message())
                        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, request.conversationId()))
                        .call()
                        .content();
        return new ChatResponse(reply);

    }

}
