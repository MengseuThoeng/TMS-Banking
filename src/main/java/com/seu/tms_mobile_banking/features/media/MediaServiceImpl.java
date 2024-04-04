package com.seu.tms_mobile_banking.features.media;

import com.seu.tms_mobile_banking.features.media.dto.MediaResponse;
import com.seu.tms_mobile_banking.util.MediaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {
    @Value("${media.server-path}")
    private String serverPath;
    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public MediaResponse uploadSingle(MultipartFile file, String folderName) {
        //Change name unique name
        String newName = UUID.randomUUID().toString();

        // get extension from file after the user upload
        String extension = MediaUtil.extractExtension(Objects.requireNonNull(file.getOriginalFilename()));
        newName+="."+extension;
        //copy file user upload to server
        Path path = Paths.get(serverPath + folderName + "\\" + newName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException io) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    io.getLocalizedMessage()
            );
        }
        return MediaResponse.builder()
                .name(newName)
                .contentType(file.getContentType())
                .extension(extension)
                .uri(String.format("%s%s/%s", baseUri, folderName, newName))
                .size(file.getSize()).build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName) {
        // Create empty arraylist wait for adding upload file
        List<MediaResponse> mediaResponsesList = new ArrayList<>();
        // Upload file
        files.forEach(file-> {
            MediaResponse mediaResponse = this.uploadSingle(file, folderName);
            mediaResponsesList.add(mediaResponse);
        });
        return mediaResponsesList;
    }

    @Override
    public MediaResponse loadMediaByName(String mediaName,String folderName) {
        //create absulote path
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);

        try{
            Resource resource = new UrlResource((path.toUri()));
            if (!resource.exists()){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Don't Found"
                );
            }
            return MediaResponse.builder()
                    .name(resource.getFilename())
                    .contentType(Files.probeContentType(path))
                    .extension(MediaUtil.extractExtension(mediaName))
                    .uri(String.format("%s%s/%s", baseUri, folderName, mediaName))
                    .size(resource.contentLength()).build();
        }catch (MalformedURLException ex){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public MediaResponse deleteMediaByName(String mediaName, String folderName) {
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);

        try {
            long size= Files.size(path);
            if(Files.deleteIfExists(path)){
                return MediaResponse.builder()
                        .name(mediaName)
                        .contentType(Files.probeContentType(path))
                        .extension(MediaUtil.extractExtension(mediaName))
                        .uri(String.format("%s%s/%s", baseUri, folderName, mediaName))
                        .size(size)
                        .build();
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Don't Found"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<MediaResponse> findAllFile(String folderName) {
        List<MediaResponse> mediaResponseList = new ArrayList<>();
        File directory = new File(serverPath + folderName);

        // Check if the directory exists and is a directory
        if (directory.exists() && directory.isDirectory()) {
            // List all files in the directory
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    MediaResponse mediaResponse = MediaResponse.builder()
                            .name(file.getName())
                            .contentType(MediaUtil.extractExtension(file.getName()))
                            .extension(MediaUtil.extractExtension(file.getName()))
                            .uri(String.format("%s%s/%s", baseUri, folderName, file.getName()))
                            .size(file.length())
                            .build();
                    mediaResponseList.add(mediaResponse);
                }
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Directory not found"
            );
        }
        return mediaResponseList;
    }

    @Override
    public ResponseEntity<byte[]> downloadMediaByName(String name, String folderName) {
        try {
            URL url = new URL(String.format("%s%s/%s", baseUri, folderName, name));

            byte[] imageData = StreamUtils.copyToByteArray(url.openStream());

            String contentType = Files.probeContentType(Paths.get(name));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDispositionFormData("attachment", name);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error downloading media", e);
        }
    }
}
