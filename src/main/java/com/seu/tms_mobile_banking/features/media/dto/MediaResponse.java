package com.seu.tms_mobile_banking.features.media.dto;

import lombok.Builder;

@Builder
public record MediaResponse(
        String name, //unique file name bro
        String contentType, //PNG Extension
        String extension,
        String uri, // https://asd/dad.png
        Long size
) {
}
