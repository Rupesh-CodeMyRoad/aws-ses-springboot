package com.rg.aws.ses.controller;

import com.rg.aws.ses.dto.AWSResponse;
import com.rg.aws.ses.dto.EmailDetails;
import com.rg.aws.ses.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> sendPersonalizedTemplateEmail(@RequestBody EmailDetails emailDetails) {
        AWSResponse response = emailService.sendPersonalizedTemplateEmail(emailDetails);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sendMailToVerifiedUsers")
    public ResponseEntity<?> sendMailToVerifiedUsers(@RequestBody EmailDetails emailDetails) {
        AWSResponse response = emailService.sendMailToVerifiedUsers(emailDetails);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sendVerificationEmail/{email}")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable String email) {
        String response = emailService.sendVerificationEmail(email);
        return ResponseEntity.ok(response);
    }


}
