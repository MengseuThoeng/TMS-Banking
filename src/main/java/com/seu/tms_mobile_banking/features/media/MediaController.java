package com.seu.tms_mobile_banking.features.media;

import com.seu.tms_mobile_banking.features.media.dto.MediaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/upload-single")
    MediaResponse uploadSingle(@RequestPart MultipartFile file){
        return mediaService.uploadSingle(file,"IMAGE");
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/upload-multiple")
    List<MediaResponse> uploadMultiple (@RequestPart List<MultipartFile> files){
        return mediaService.uploadMultiple(files,"IMAGE");
    }

    @GetMapping("/{mediaName}")
    MediaResponse getMediaByName (@PathVariable String mediaName){
        return mediaService.loadMediaByName(mediaName,"IMAGE");
    }
    @DeleteMapping("/{mediaName}")
    MediaResponse deleteByName(@PathVariable String mediaName){
        return mediaService.deleteMediaByName(mediaName,"IMAGE");
    }
    @GetMapping("/all")
    List<MediaResponse> findAllFiles (){
        return mediaService.findAllFile("IMAGE");
    }
    @GetMapping("/download/{name}")
    ResponseEntity<byte[]>downloadMediaByName(@PathVariable String name){
        return mediaService.downloadMediaByName(name,"IMAGE");
    }
}