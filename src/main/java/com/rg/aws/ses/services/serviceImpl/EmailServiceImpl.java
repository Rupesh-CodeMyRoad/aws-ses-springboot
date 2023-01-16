package com.rg.aws.ses.services.serviceImpl;

import com.rg.aws.ses.Dto.EmailDetails;
import com.rg.aws.ses.services.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration config;

    @Override
    public String sendMessage(EmailDetails emailDetails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailDetails.getFromEmail());
        simpleMailMessage.setTo(emailDetails.getToEmail());
        simpleMailMessage.setSubject(emailDetails.getSubject());
        simpleMailMessage.setText(emailDetails.getBody());
        try {
            this.mailSender.send(simpleMailMessage);
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
            javaMailSender.send(message);

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
            javaMailSender.send(message);

        } catch (Exception e) {
            return "Exception: " + e.getLocalizedMessage();
        }
        return "Mail Send Success";
    }
}
