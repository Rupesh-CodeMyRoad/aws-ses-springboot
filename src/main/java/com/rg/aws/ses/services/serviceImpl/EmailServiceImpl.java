package com.rg.aws.ses.services.serviceImpl;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.rg.aws.ses.dto.AWSResponse;
import com.rg.aws.ses.dto.EmailDetails;
import com.rg.aws.ses.exception.TemplateException;
import com.rg.aws.ses.services.EmailService;
import com.rg.aws.ses.utils.AWSErrorCode;
import com.rg.aws.ses.utils.EmailValidation;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration config;

    @Autowired
    private AmazonSimpleEmailService simpleEmailService;


    @Override
    public String sendMessage(EmailDetails emailDetails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        if (EmailValidation.validate(emailDetails.getFromEmail())) {
            simpleMailMessage.setFrom(emailDetails.getFromEmail());
        } else {
            throw new TemplateException("Email address Mismatch");
        }
        simpleMailMessage.setTo(emailDetails.getToEmail());
        simpleMailMessage.setSubject(emailDetails.getSubject());
        simpleMailMessage.setText(emailDetails.getBody());
//        simpleMailMessage.setCc("rupeshgr015330@nec.edu.np");
//        simpleMailMessage.setBcc("rupeshgaudel3@gmail.com");
        try {
            this.mailSender.send(simpleMailMessage);
        } catch (MailException me) {
            log.error("mail cann't be send due to :{}", ExceptionUtils.getStackTrace(me));
            throw new TemplateException("mail exception occure please varify your request once and try again", me);
        } catch (Exception e) {
            return "Exception: " + e.getLocalizedMessage();
        }
        return "Mail Send Success";
    }

    @Override
    public String sendAttachMessage(EmailDetails emailDetails) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            // Set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            // Add attachment
            helper.addAttachment(emailDetails.getAttachment().getOriginalFilename(), emailDetails.getAttachment());
            helper.setTo(Objects.requireNonNull(emailDetails.getToEmail()));
            helper.setText(Objects.requireNonNull(emailDetails.getBody()));
            helper.setSubject(Objects.requireNonNull(emailDetails.getSubject()));
            helper.setFrom(Objects.requireNonNull(emailDetails.getFromEmail()));
//            helper.setCc("aaa@gmail.com");
//            helper.setBcc("abc@gmail.com");
            javaMailSender.send(message);

        } catch (MailException me) {
            log.error("mail cann't be send due to :{}", ExceptionUtils.getStackTrace(me));
            throw new TemplateException("mail exception occure please varify your request once and try again", me);
        } catch (Exception e) {
            return "Exception: " + e.getLocalizedMessage();
        }
        return "Mail Send Success";
    }

    @Override
    public String sendTemplateAttachEmail(EmailDetails emailDetails) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            // Set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template t = config.getTemplate("email-template.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("Name", "Rupesh Gaudel Regmi");
            model.put("location", "Pokhara, Nepal");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            // Add attachment
            helper.addAttachment(emailDetails.getAttachment().getOriginalFilename(), emailDetails.getAttachment());
            helper.setTo(Objects.requireNonNull(emailDetails.getToEmail()));
            helper.setText(html, true);
            helper.setSubject(Objects.requireNonNull(emailDetails.getSubject()));
            helper.setFrom(Objects.requireNonNull(emailDetails.getFromEmail()));
//            helper.setCc("aaa@gmail.com");
//            helper.setBcc("abc@gmail.com");
            javaMailSender.send(message);

        } catch (Exception e) {
            return "Exception: " + e.getLocalizedMessage();
        }
        return "Mail Send Success";
    }

    public AWSResponse sendPersonalizedTemplateEmail(EmailDetails emailDetails) {
        return sendAwsMail(emailDetails);
    }

    @Override
    public AWSResponse sendMailToVerifiedUsers(EmailDetails emailDetails) {

        List<String> verifiedUsers = ListEmailIdentities.listSESIdentities();
        emailDetails.setToEmailList(verifiedUsers);
        return sendAwsMail(emailDetails);
    }

    @Override
    public String sendVerificationEmail(String email) {
        SendCustomVerificationEmailRequest sendCustomVerificationEmailRequest = new SendCustomVerificationEmailRequest();
        sendCustomVerificationEmailRequest.setEmailAddress(email);
        sendCustomVerificationEmailRequest.setTemplateName("MyTemp");
        try {
            SendCustomVerificationEmailResult response = simpleEmailService.sendCustomVerificationEmail(sendCustomVerificationEmailRequest);
            System.out.println(response);
        } catch (AmazonSimpleEmailServiceException e) {
            throw new RuntimeException(e.getErrorMessage());
        }
        return "test";
    }


    private AWSResponse sendAwsMail(EmailDetails emailDetails) {
        AWSResponse awsResponse = null;
        try {
            EmailValidation.validateEmails(emailDetails);
            Destination destination = new Destination();
            List<String> toAddresses = emailDetails.getToEmailList();
            destination.setToAddresses(toAddresses);
//            destination.setCcAddresses(toAddresses);
//            destination.setBccAddresses(toAddresses);

            SendTemplatedEmailRequest templatedEmailRequest = new SendTemplatedEmailRequest();
            templatedEmailRequest.withDestination(destination);
            templatedEmailRequest.withTemplate("MyTemp");
            templatedEmailRequest.withTemplateData("{ \"name\":\"Rupesh Regmi\"}");
            templatedEmailRequest.withSource(emailDetails.getFromEmail());
            SendTemplatedEmailResult response = simpleEmailService.sendTemplatedEmail(templatedEmailRequest);
            if (response.getSdkHttpMetadata().getHttpStatusCode() == 200) {
                awsResponse = new AWSResponse().builder()
                        .messageId(response.getMessageId())
                        .awsRequestId(response.getSdkResponseMetadata().getRequestId())
                        .statusCode(response.getSdkHttpMetadata().getHttpStatusCode())
                        .build();
            }
        } catch (AmazonSimpleEmailServiceException e) {
            log.error("Mail cannot be send due to :{}", e.getStackTrace());
            handleException(e);
        }
        return awsResponse;
    }

    private void handleException(AmazonSimpleEmailServiceException e) {
        if (e.getStatusCode() == 400 && e.getErrorCode().equals(AWSErrorCode.TEMPLATE_DOES_NOT_EXIST.getErrorCode())) {
            throw new TemplateException(e.getErrorMessage(), e);
        }
    }
}
