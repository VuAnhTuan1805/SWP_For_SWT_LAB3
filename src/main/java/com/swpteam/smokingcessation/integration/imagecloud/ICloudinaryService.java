package com.swpteam.smokingcessation.integration.imagecloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ICloudinaryService {

    String uploadImage(MultipartFile file) throws IOException;

    String generatePublicValue(String originalName);

    String[] getFileName(String originalName);
}
