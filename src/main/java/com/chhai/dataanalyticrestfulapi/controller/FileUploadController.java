package com.chhai.dataanalyticrestfulapi.controller;

import com.chhai.dataanalyticrestfulapi.model.response.FileUploadResponse;
import com.chhai.dataanalyticrestfulapi.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;
    @Value("/images")
    String path;
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> fileResponseResponseEntity(@RequestParam("image") MultipartFile file){
        String filename = null;
        try{
            filename = this.fileUploadService.Image(path,file);
        }catch(IOException e){
            return new ResponseEntity<FileUploadResponse>(new FileUploadResponse(null,"image can not upload!!!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new FileUploadResponse(filename,"image uploaded successfully!!!"),HttpStatus.OK);
    }
}
