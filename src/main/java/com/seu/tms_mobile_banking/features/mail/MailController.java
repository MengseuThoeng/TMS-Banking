package com.seu.tms_mobile_banking.features.mail;

import com.seu.tms_mobile_banking.features.mail.dto.MailRequest;
import com.seu.tms_mobile_banking.features.mail.dto.MailResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {
    private final MailService mailService;
    @PostMapping
    MailResponse send (@Valid @RequestBody MailRequest request){
        return mailService.send(request);
    }
}
