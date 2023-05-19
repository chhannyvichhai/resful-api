package com.chhai.dataanalyticrestfulapi.controller;

import com.chhai.dataanalyticrestfulapi.model.response.FileResponse;
import com.chhai.dataanalyticrestfulapi.service.FileStorageService;
import com.chhai.dataanalyticrestfulapi.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/file-service")
public class FileRestController {

    @Autowired
    FileStorageService fileStorageService;

    // allowed extension (jpg,png,...)
    private final List<String> ALLOWED_EXTENSION = List.of("jpg", "png", "jpeg");
    private final long MAX_FILE_SIZE = 1024*1024 * 5; // 5 = 5MB

    // size profile image

    @PostMapping("/file-upload")
    public Response<FileResponse> fileUpload(@RequestParam("file") MultipartFile file) throws Exception{

           FileResponse response = uploadFile(file);
           return Response.<FileResponse>ok().setPayload(response).setMessage("Successfully upload a file");

    }

    @PostMapping("/multiple-file-upload")
    public Response<List<FileResponse>> uploadMultipleFiles (@RequestParam("files") MultipartFile[] files){

            List<FileResponse> responses = Arrays.stream(files).map(file ->{
                try {
                    return uploadFile(file);
                }catch (Exception ex){
                    throw new RuntimeException(ex);
                }
            }).toList();
            return Response.<List<FileResponse>>ok().setPayload(responses).setMessage("Successfully upload a file");


    }

    // for delete single and multiple
    @DeleteMapping("/deletefile/{filename}")
    public String deleteSingleFile(@PathVariable String filename){
        // method definition will be written later
        String result = fileStorageService.deleteFileByName(filename);
        return result;
    }

    @DeleteMapping("/delete-all-file")
    public String delelteAllFiles(){
        String results = fileStorageService.deleteAllFiles();
        return results;
    }

    // for download file handling
    @GetMapping("/download-file/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename, HttpServletRequest request) throws Exception{
        Resource resource = fileStorageService.loadFileResources(filename);
        // Try to determine file content
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (Exception ex){
            System.out.println("Error getting content type is: " + ex.getMessage());
        }
        if (contentType==null){
            contentType="application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ resource.getFilename()+"\"").body(resource);
    }

    private FileResponse uploadFile(MultipartFile file){
        // file is empty
        if (file.isEmpty())
            throw new IllegalArgumentException("Files cannot be empty");
        // file size
        if (file.getSize()>MAX_FILE_SIZE)
            throw new MaxUploadSizeExceededException(MAX_FILE_SIZE);
        // extension
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (!ALLOWED_EXTENSION.contains(extension.toLowerCase())){
            throw new IllegalArgumentException("Allowed Extension are 'jpg', 'jpeg', 'png' ");
        }

        // the old way
        //String[] fileParts = file.getOriginalFilename().split("\\.");
        //String extension = fileParts[1];

        String filename = fileStorageService.uploadFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file-service/download-file/")
                .path(filename)
                .toUriString();

        return new FileResponse().setFilename(filename).setFileDownloadUri(fileDownloadUri)
                .setFileDownloadUri(fileDownloadUri)
                .setFileType(file.getContentType())
                .setSize(file.getSize());
    }

}
