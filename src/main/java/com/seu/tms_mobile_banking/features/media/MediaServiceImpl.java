package com.seu.tms_mobile_banking.features.media;

import com.seu.tms_mobile_banking.features.media.dto.MediaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
        int lastDotIndex = file.getOriginalFilename()
                .lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastDotIndex + 1);
        newName += "." + extension;

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
}
