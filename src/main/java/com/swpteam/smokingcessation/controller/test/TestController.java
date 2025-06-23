package com.swpteam.smokingcessation.controller.test;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.integration.AI.AIService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/test")
@Tag(name = "Testing", description = "For testing services")
public class TestController {

    AIService aiService;

    @PostMapping
    ResponseEntity<ApiResponse<String>> createAccount(@RequestBody @Valid TestRequest request) {
        String result;
        //

        result = aiService.chat(request.getPrompt());

        //
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(200)
                        .message("Success")
                        .result(result)
                        .build());
    }
}
