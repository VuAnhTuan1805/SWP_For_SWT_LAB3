package com.swpteam.smokingcessation.controller.v1.upload;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.integration.imagecloud.CloudinaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Upload image", description = "Manage upload-related operations")
public class UploadFileController {

    CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        String resultUrl = cloudinaryService.uploadImage(file);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(SuccessCode.UPLOAD_IMAGE.getCode())
                        .message(SuccessCode.UPLOAD_IMAGE.getMessage())
                        .result(resultUrl)
                        .build()
        );
    }
}
