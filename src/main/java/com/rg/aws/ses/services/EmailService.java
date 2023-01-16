package com.rg.aws.ses.services;

import com.rg.aws.ses.Dto.EmailDetails;
import org.springframework.stereotype.Service;

public interface EmailService {
    String sendMessage(EmailDetails emailDetails);

    String sendAttachMessage(EmailDetails emailDetails);

    String sendTemplateAttachEmail(EmailDetails emailDetails);

    String sendPersonalizedTemplateEmail(EmailDetails emailDetails);
}
