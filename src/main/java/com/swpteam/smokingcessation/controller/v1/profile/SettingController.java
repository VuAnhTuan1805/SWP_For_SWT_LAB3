package com.swpteam.smokingcessation.controller.v1.profile;

import com.swpteam.smokingcessation.domain.dto.setting.SettingRequest;
import com.swpteam.smokingcessation.domain.dto.setting.SettingResponse;
import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.service.interfaces.profile.ISettingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Setting", description = "Manage setting-related operations")
public class SettingController {

    ISettingService settingService;

    @PutMapping("/{accountId}")
    ApiResponse<SettingResponse> updateSetting(@PathVariable String accountId, @RequestBody @Valid SettingRequest request) {
        return ApiResponse.<SettingResponse>builder()
                .result(settingService.updateSetting(accountId, request))
                .build();
    }

    @GetMapping("/{accountId}")
    ApiResponse<SettingResponse> getSettingById(@PathVariable String accountId) {
        return ApiResponse.<SettingResponse>builder()
                .result(settingService.getSettingByAccountId(accountId))
                .build();
    }
}
