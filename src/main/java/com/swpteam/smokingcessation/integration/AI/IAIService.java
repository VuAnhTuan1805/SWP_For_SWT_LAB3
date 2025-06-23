package com.swpteam.smokingcessation.integration.AI;

public interface IAIService {

    String chat(String prompt);

    String chatWithPlatformContext(String message);
}
