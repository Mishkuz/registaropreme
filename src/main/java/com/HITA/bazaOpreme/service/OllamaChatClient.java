/*package com.HITA.bazaOpreme.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OllamaChatClient {

    private ChatClient chatClient;

    public OllamaChatClient(ChatClient.Builder clientBuilder) {
        this.chatClient = clientBuilder.build();
    }

    @GetMapping("/ai")
    public List <Generation> hello() {
        PromptTemplate promptTemplate = new PromptTemplate("What is your name?");
        Prompt prompt = promptTemplate.create();
        ChatClient.ChatClientRequest.CallPromptResponseSpec responseSpec = chatClient.prompt(prompt).call();
        return (List<Generation>) responseSpec.chatResponse().getResult();
    }
}
*/