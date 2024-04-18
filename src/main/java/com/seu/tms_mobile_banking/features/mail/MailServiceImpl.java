package com.seu.tms_mobile_banking.features.mail;

import com.seu.tms_mobile_banking.features.mail.dto.MailRequest;
import com.seu.tms_mobile_banking.features.mail.dto.MailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    @Override
    public MailResponse send(MailRequest request) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        Context context = new Context();
        context.setVariable("name","Hello "+request.to()+"💕💕");

        String htmlTemplate = templateEngine.process("mail/sample",context);
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true); // Set true for multipart
            mimeMessageHelper.setTo(request.to());
            mimeMessageHelper.setSubject(request.subject());
            mimeMessageHelper.setText(htmlTemplate,true);
//
//            Path imagePath = Paths.get(request.image());
//            byte[] imageBytes = Files.readAllBytes(imagePath);
//
//            mimeMessageHelper.addAttachment("image.jpg", new ByteArrayResource(imageBytes), "image/jpeg");
        } catch (MessagingException e) {
            throw new RuntimeException("Error creating MimeMessageHelper", e);
        }
//        } catch (IOException e) {
//            throw new RuntimeException("Error reading image file", e);
//        }
        javaMailSender.send(mimeMessage);
        return new MailResponse("Mail has been sent to your email");
    }

}
