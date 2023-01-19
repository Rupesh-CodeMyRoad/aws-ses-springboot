package com.rg.aws.ses.services;

import com.rg.aws.ses.dto.AWSResponse;
import com.rg.aws.ses.dto.EmailDetails;

public interface EmailService {
//    String sendMessage(EmailDetails emailDetails);
//
//    String sendAttachMessage(EmailDetails emailDetails);
//
//    String sendTemplateAttachEmail(EmailDetails emailDetails);

    AWSResponse sendPersonalizedTemplateEmail(EmailDetails emailDetails);
}
