package com.rg.aws.ses.services.serviceImpl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.AmazonSimpleEmailServiceException;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendCustomVerificationEmailRequest;
import com.amazonaws.services.simpleemail.model.SendCustomVerificationEmailResult;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailResult;
import com.rg.aws.ses.dto.AWSResponse;
import com.rg.aws.ses.dto.EmailDetails;
import com.rg.aws.ses.exception.EmailValidationException;
import com.rg.aws.ses.exception.TemplateException;
import com.rg.aws.ses.services.EmailService;
import com.rg.aws.ses.utils.AWSErrorCode;
import com.rg.aws.ses.utils.EmailValidation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

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
	public String sendAttachMessage(EmailDetails emailDetails) {
		log.info("going to send email for :" + emailDetails);
		String response;
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			if (CollectionUtils.isNotEmpty(emailDetails.getAttachment())) {
				emailDetails.getAttachment().stream().forEach(file->{
					try {
						helper.addAttachment(file.getOriginalFilename(), file);
					} catch (MessagingException e) {
						log.error("error while adding attachment to file");
						e.printStackTrace();				
					}
				});		
			}
			helper.setTo(Objects.requireNonNull(emailDetails.getToEmail()));
			helper.setText(Objects.requireNonNull(emailDetails.getBody()));
			helper.setSubject(Objects.requireNonNull(emailDetails.getSubject()));
			helper.setFrom(Objects.requireNonNull(emailDetails.getFromEmail()));

			javaMailSender.send(message);
			response = "Mail Send Success";
		} catch (MailException me) {
			log.error("mail cann't be send due to :{}", ExceptionUtils.getStackTrace(me));
			throw new EmailValidationException("mail exception occure please varify your request once and try again",
					me);
		} catch (Exception e) {
			log.error("mail cann't be send due to :{}", ExceptionUtils.getStackTrace(e));
			response = "something went wrong";
		}
		return response;
	}
   
	@Override
	public String sendTemplateAttachEmail(EmailDetails emailDetails) {

		try {
			MimeMessage message = javaMailSender.createMimeMessage();

			// Set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Template t = config.getTemplate(emailDetails.getTemplateName());
			Map<String, Object> model = new HashMap<>();
			model.put("Name", "Rupesh Gaudel Regmi");
			model.put("location", "Pokhara, Nepal");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			// Add attachment
			for (MultipartFile file : emailDetails.getAttachment()) {
				helper.addAttachment(file.getOriginalFilename(), file);
			}
			helper.setTo(Objects.requireNonNull(emailDetails.getToEmail()));
			helper.setText(html, true);
			helper.setSubject(Objects.requireNonNull(emailDetails.getSubject()));
			helper.setFrom(Objects.requireNonNull(emailDetails.getFromEmail()));
			javaMailSender.send(message);

		} catch (MailException me) {
			log.error("mail cann't be send due to :{}", ExceptionUtils.getStackTrace(me));
			throw new TemplateException("mail exception occure please varify your request once and try again", me);
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
			SendCustomVerificationEmailResult response = simpleEmailService
					.sendCustomVerificationEmail(sendCustomVerificationEmailRequest);
			System.out.println(response);
		} catch (AmazonSimpleEmailServiceException e) {
			throw new RuntimeException(e.getErrorMessage());
		}
		 catch (Exception e) {
			 log.error("Mail cannot be send due to :{}", e.getStackTrace());
			}
		return "varification mail send";
	}

	private AWSResponse sendAwsMail(EmailDetails emailDetails) {
		AWSResponse awsResponse = null;
		try {
			EmailValidation.validateEmails(emailDetails);
			Destination destination = new Destination();
			List<String> toAddresses = emailDetails.getToEmailList();
			destination.setToAddresses(toAddresses);
			SendTemplatedEmailRequest templatedEmailRequest = new SendTemplatedEmailRequest();
			templatedEmailRequest.withDestination(destination);
			templatedEmailRequest.withTemplate(emailDetails.getTemplateName());
			templatedEmailRequest.withTemplateData("{ \"name\":\"Rupesh Regmi\"}");
			templatedEmailRequest.withSource(emailDetails.getFromEmail());
			SendTemplatedEmailResult response = simpleEmailService.sendTemplatedEmail(templatedEmailRequest);
			if (response.getSdkHttpMetadata().getHttpStatusCode() == 200) {
				awsResponse = new AWSResponse().builder().messageId(response.getMessageId())
						.awsRequestId(response.getSdkResponseMetadata().getRequestId())
						.statusCode(response.getSdkHttpMetadata().getHttpStatusCode()).build();
			}

		} catch (AmazonSimpleEmailServiceException e) {
			log.error("Mail cannot be send due to :{}", e.getStackTrace());
			handleException(e);
		}
		 catch (Exception e) {
			 log.error("Mail cannot be send due to :{}", e.getStackTrace());
			}
		return awsResponse;
	}

	private void handleException(AmazonSimpleEmailServiceException e) {
		if (e.getStatusCode() == 400 && e.getErrorCode().equals(AWSErrorCode.TEMPLATE_DOES_NOT_EXIST.getErrorCode())) {
			throw new TemplateException(e.getErrorMessage(), e);
		}
	}
}
