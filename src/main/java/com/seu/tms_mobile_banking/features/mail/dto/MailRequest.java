package com.seu.tms_mobile_banking.features.mail.dto;

import jakarta.validation.constraints.NotBlank;

public record MailRequest(
        @NotBlank(message = "EMAIL IS REQUIRED")
        String to,
        @NotBlank(message = "SUBJECT IS REQUIRED")
        String subject,

        String text, // body of mail

        String image
) {
}
