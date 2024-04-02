package com.seu.tms_mobile_banking.features.media;

import com.seu.tms_mobile_banking.features.media.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    MediaResponse uploadSingle(MultipartFile file,String folderName);
}
