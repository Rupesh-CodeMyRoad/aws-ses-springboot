package com.rg.aws.ses.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rg.aws.ses.controller.AwsController;
import com.rg.aws.ses.dto.AWSResponse;
import com.rg.aws.ses.dto.EmailDetails;
import com.rg.aws.ses.services.EmailService;

@SpringBootTest
public class AwsControllerTest {

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;
	
	final String from="kajalrai3010@gmail.com";
	final String[] to={"kajalrai0512@gmail.com"};
	
	@Autowired
	AwsController awsController;
	
	@Autowired
	EmailService emailService;
	
	@Test
	 public void sendPersonalizedTemplateEmail() {
		AWSResponse awsResponse = new AWSResponse();
		awsResponse.setStatusCode(200);
		
		//awsResponse.setAwsRequestId(accessKey);
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setFromEmail(from);
		emailDetails.setToEmailList(to);
		emailDetails.setSubject("Invitation for new year celebration");
		emailDetails.setBody("The happiness of the upcoming year is a few days away. We are waiting for you to join us on the dance floor on new year’s eve. ");
		//when(emailService.sendPersonalizedTemplateEmail(emailDetails)).thenReturn(awsResponse);
		ResponseEntity<?> responseEntity = awsController.sendPersonalizedTemplateEmail(emailDetails);
		assertEquals(responseEntity.getStatusCode().value(),awsResponse.getStatusCode());
		//AWSResponse awsResponse = emailServiceImpl.sendPersonalizedTemplateEmail(emailDetails);
		//System.out.println(awsResponse);
    }

}
