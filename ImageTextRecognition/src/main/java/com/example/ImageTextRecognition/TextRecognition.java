package com.example.ImageTextRecognition;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class TextRecognition {


    private final org.springframework.ai.chat.client.ChatClient chatClient;

    public TextRecognition(ChatClient.Builder chatClient) {
        this.chatClient =  chatClient.build();
    }

    @GetMapping("/ImageTextRecognition")
    public String getDalle() throws IOException {
        Resource ressource = new ClassPathResource("images.jpg");
        byte[] data = ressource.getContentAsByteArray();
        String userMessageText = """
                Analyse l'image qui contient du texte manuscrit et donne moi , au format json comment le total de facture est répartie entre les différents produits.
                """;
        UserMessage userMessage = new UserMessage(userMessageText, List.of(
                new Media(MimeTypeUtils.IMAGE_JPEG, data)
        ));
        Prompt prompt = new Prompt(userMessage);
        return chatClient.prompt(prompt).call().content();
    }

}

