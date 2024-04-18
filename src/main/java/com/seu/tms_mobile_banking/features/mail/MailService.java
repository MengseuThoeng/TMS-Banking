package com.seu.tms_mobile_banking.features.mail;

import com.seu.tms_mobile_banking.features.mail.dto.MailRequest;
import com.seu.tms_mobile_banking.features.mail.dto.MailResponse;

public interface MailService {
    MailResponse send(MailRequest request);
}
