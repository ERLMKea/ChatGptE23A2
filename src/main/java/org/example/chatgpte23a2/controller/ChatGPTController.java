package org.example.chatgpte23a2.controller;

import org.example.chatgpte23a2.dto.ChatRequest;
import org.example.chatgpte23a2.dto.Choice;
import org.example.chatgpte23a2.dto.Message;
import org.example.chatgpte23a2.dto.Usage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChatGPTController {

    @Value("${openai.api.key}")
    private String openapikey;

    private final WebClient webClient;

    public ChatGPTController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/chat/completions").build();
    }

    @GetMapping("/chat")
    public Map<String, Object> chatWithGPT(@RequestParam String message) {
        ChatRequest chatRequest = new ChatRequest(); //ChatRequest objekt har jeg dannet med https://www.jsonschema2pojo.or g/ værktøj
        chatRequest.setModel("gpt-3.5-turbo"); //vælg rigtig model. se powerpoint
        List<Message> lstMessages = new ArrayList<>(); //en liste af messages med roller
        lstMessages.add(new Message("system", "You are a helpful assistant."));
        lstMessages.add(new Message("user", "Where is " + message));
        chatRequest.setMessages(lstMessages);
        chatRequest.setN(3); //n er antal svar fra chatgpt
        chatRequest.setTemperature(1); //jo højere jo mere fantasifuldt svar (se powerpoint)
        chatRequest.setMaxTokens(30); //længde af svar
        chatRequest.setStream(false); //stream = true, er for viderekomne, der kommer flere svar asynkront
        chatRequest.setPresencePenalty(1); //noget med ikke at gentage sig. se powerpoint


        List<Choice> lst = response.getChoices();
        Usage usg = response.getUsage();

        Map<String, Object> map = new HashMap<>();
        map.put("Usage", usg);
        map.put("Choices", lst);

        return map;
    }

}
