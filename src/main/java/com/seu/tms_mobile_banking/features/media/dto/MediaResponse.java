package com.seu.tms_mobile_banking.features.media.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record MediaResponse(
        String name, //unique file name bro
        String contentType, //PNG Extension
        String extension,
        String uri, // https://asd/dad.png
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long size
) {
}
