package com.chhai.dataanalyticrestfulapi.service.serviceImpl;

import com.chhai.dataanalyticrestfulapi.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final String serverLocation = "src/main/resources/imagesv2/";
    Path fileLocationStorage;
    public FileStorageServiceImpl(){
//        fileLocationStorage = (Path) Paths.get("src/main/resources/imagesv2/");
        fileLocationStorage = Paths.get(serverLocation);
        try {
            if(!Files.exists(fileLocationStorage)){
                Files.createDirectories(fileLocationStorage);
            }else {
                System.out.println("Directory is already existed !");
            }
        }catch (Exception ex){
            System.out.println("Error creating directory : "+ ex.getMessage());
        }
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String[] fileCompartments = filename.split("\\.");

        filename = UUID.randomUUID()+"."+fileCompartments[1];

       Path resolvePath = fileLocationStorage.resolve(filename);
       try {
           Files.copy(file.getInputStream(),resolvePath, StandardCopyOption.REPLACE_EXISTING);
           return filename;
       }catch (Exception ex){
           return ex.getMessage();
       }
    }

    @Override
    public String deleteFileByName(String filename) {

        Path imagesLocation = Paths.get(serverLocation);
        List<File> allFiles = List.of(imagesLocation.toFile().listFiles());

        // filter file that we will delete
        File deletedFile = allFiles.stream().filter(
                file -> file.getName().equals(filename)
        ).findFirst().orElse(null);

        try {
            if (deletedFile!=null){
            Files.delete(deletedFile.toPath());
                return "Delete file successfully!";
            }else {
                return "file with "+filename+ "doesn't exit";
            }
        }catch (Exception ex){
            System.out.println("Error delete file by name : "+ ex.getMessage());
            return "Exception occurred! failed to delete file by name";
        }
    }

    @Override
    public String deleteAllFiles() {
        Path imageLocation = Paths.get(serverLocation);
        File[] files = imageLocation.toFile().listFiles();
        try {
            if (files == null || files.length ==0){
                return "There is no file to delete!!";
            }
            for(File file : files){
                Files.delete(file.toPath());
            }
            return "Successfully delete all files";
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Exception delete all files : "+ ex.getMessage());
            return "Filed to delete all files ! Exception occurred";
        }
    }

    @Override
    public Resource loadFileResources(String filename) throws Exception {
        Path resourcesPath = this.fileLocationStorage.resolve(filename).normalize();
        try {
            Resource resource = new UrlResource(resourcesPath.toUri());
            if (resource.exists()){
                return resource;
            }else {
                throw new Exception("Resources doesn't exist!");
            }
        }catch (Exception ex){
            throw new Exception("Exception Occurred! Failed to download the image");
        }
    }
}
