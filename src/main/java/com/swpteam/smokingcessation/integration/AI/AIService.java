package com.swpteam.smokingcessation.integration.AI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swpteam.smokingcessation.constant.ResourceFilePaths;
import com.swpteam.smokingcessation.utils.FileLoaderUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AIService implements IAIService {

    ChatClient chatClient;
    FileLoaderUtil fileLoaderUtil;

    public AIService(ChatClient.Builder builder, FileLoaderUtil fileLoaderUtil) {
        chatClient = builder.build();
        this.fileLoaderUtil = fileLoaderUtil;
    }

    @Override
    public String chat(String prompt) {
        return chatClient.prompt(prompt).call().content();
    }

    @Override
    public String chatWithPlatformContext(String message) {
        String preContext = getPlatformContextPrompt();

        String prompt = preContext + message;

        return chatClient.prompt(prompt).call().content();
    }

    private String getPlatformContextPrompt() {
        Map<String, String> context = fileLoaderUtil.loadJsonAsMap(ResourceFilePaths.PLATFORM_CONTEXT);

        StringBuilder prompt = new StringBuilder("""
                You are a helpful assistant for a Smoking Cessation Platform.
                
                You must:
                - Reply in the same language as the user question
                - Keep your replies short, focused, and conversational — like chat messages
                - Only provide relevant and concise information
                
                Here is the background context:
                
                """);

        context.forEach((key, value) -> {
            String title = key.replace("-", " ");
            title = Character.toUpperCase(title.charAt(0)) + title.substring(1);
            prompt.append(title).append(":\n").append(value).append("\n\n");
        });

        return prompt.toString().trim();
    }
}
