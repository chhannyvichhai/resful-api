package com.chhai.dataanalyticrestfulapi.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String uploadFile(MultipartFile file);
    String deleteFileByName(String filename);
    String deleteAllFiles();

    // load resources for downloading
    Resource loadFileResources(String filename) throws Exception;
}
