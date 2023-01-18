package com.rg.aws.ses.controller;

import com.rg.aws.ses.dto.EmailDetails;
import com.rg.aws.ses.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwsController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailDetails emailDetails) {
        return emailService.sendMessage(emailDetails);
    }

    @PostMapping("/sendAttachEmail")
    public String sendAttachEmail(@ModelAttribute EmailDetails emailDetails) {
        return emailService.sendAttachMessage(emailDetails);
    }

    @PostMapping("/sendTemplateAttachEmail")
    public String sendTemplateAttachEmail(@ModelAttribute EmailDetails emailDetails) {
        return emailService.sendTemplateAttachEmail(emailDetails);
    }

    @PostMapping("/sendPersonalizedTemplateEmail")
    public String sendPersonalizedTemplateEmail(@RequestBody EmailDetails emailDetails) {
        return emailService.sendPersonalizedTemplateEmail(emailDetails);
    }


}
