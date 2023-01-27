package com.rg.aws.ses.controller;

import com.rg.aws.ses.dto.AWSResponse;
import com.rg.aws.ses.dto.EmailDetails;
import com.rg.aws.ses.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class AwsController {

    @Autowired
    private EmailService emailService;

    /**
     * @param emailDetails
     * @return
     */
   /* @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailDetails emailDetails) {
        return emailService.sendMessage(emailDetails);
    }*/

    /**
     * @param emailDetails
     * @return
     */
    @PostMapping("/sendAttachEmail")
    public String sendAttachEmail(@ModelAttribute EmailDetails emailDetails) {
        return emailService.sendAttachMessage(emailDetails);
    }

    /**
     * @param emailDetails
     * @return
     */
    @PostMapping("/sendTemplateAttachEmail")
    public String sendTemplateAttachEmail(@ModelAttribute EmailDetails emailDetails) {
        return emailService.sendTemplateAttachEmail(emailDetails);
    }

    /**
     * @param emailDetails
     * @return
     */
    @PostMapping("/sendPersonalizedTemplateEmail")
    public Mono<AWSResponse> sendPersonalizedTemplateEmail(@RequestBody EmailDetails emailDetails) {
        AWSResponse response = emailService.sendPersonalizedTemplateEmail(emailDetails);
        Mono<AWSResponse> data = Mono.just(response);
        return data;
    }

    /**
     * @param emailDetails
     * @return
     */
    @PostMapping("/sendMailToVerifiedUsers")
    public ResponseEntity<?> sendMailToVerifiedUsers(@RequestBody EmailDetails emailDetails) {
        AWSResponse response = emailService.sendMailToVerifiedUsers(emailDetails);
        return ResponseEntity.ok(response);
    }

    /**
     * @param email
     * @return
     */
    @PostMapping("/sendVerificationEmail/{email}")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable String email) {
        String response = emailService.sendVerificationEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}")
    private Mono<String> getEmployeeById(@PathVariable String name) {
        System.out.println("Calling SES");
        Mono<String> response = Mono.just(name+" Gaudel Regmi");
        return response;
    }

}
