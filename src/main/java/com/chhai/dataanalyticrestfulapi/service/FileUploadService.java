package com.chhai.dataanalyticrestfulapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    String Image(String path, MultipartFile file) throws IOException;
}
