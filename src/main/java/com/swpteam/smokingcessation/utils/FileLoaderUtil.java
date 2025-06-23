package com.swpteam.smokingcessation.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swpteam.smokingcessation.domain.dto.plan.PhaseTemplateResponse;
import com.swpteam.smokingcessation.domain.dto.plan.PlanTemplateResponse;
import com.swpteam.smokingcessation.domain.dto.plan.PlanTemplateWrapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileLoaderUtil {

    ObjectMapper objectMapper;

    // Load raw file content as String
    public String loadFileAsString(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            try (InputStream inputStream = resource.getInputStream()) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load file: " + path, e);
        }
    }

    // Load JSON and convert to Map<String, String>
    public Map<String, String> loadJsonAsMap(String path) {
        try {
            String json = loadFileAsString(path);
            return objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON file: " + path, e);
        }
    }

    // Load plan-template.json into List<PlanLevelDto>
    public List<PlanTemplateResponse> loadPlanTemplate(String path) {
        try {
            InputStream inputStream = new ClassPathResource(path).getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            PlanTemplateWrapper wrapper = objectMapper.readValue(inputStream, PlanTemplateWrapper.class);
            return wrapper.getLevels();
        } catch (IOException e) {
            log.error("Failed to load plan template from: {}", path, e);
            throw new RuntimeException("Unable to load plan template", e);
        }
    }
}
