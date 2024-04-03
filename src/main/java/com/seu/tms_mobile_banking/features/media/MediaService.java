package com.seu.tms_mobile_banking.features.media;

import com.seu.tms_mobile_banking.features.media.dto.MediaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    MediaResponse uploadSingle(MultipartFile file,String folderName);
    List<MediaResponse> uploadMultiple (List<MultipartFile> files,String folderName);
    MediaResponse loadMediaByName(String mediaName,String folderName);

    MediaResponse deleteMediaByName(String mediaName,String folderName);
    List<MediaResponse> findAllFile(String folderName);
    ResponseEntity<byte[]> downloadMediaByName(String name, String folderName);
}
