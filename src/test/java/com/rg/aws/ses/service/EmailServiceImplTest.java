package com.rg.aws.ses.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.rg.aws.ses.dto.AWSResponse;
import com.rg.aws.ses.dto.EmailDetails;
import com.rg.aws.ses.services.serviceImpl.EmailServiceImpl;

@SpringBootTest
public class EmailServiceImplTest {

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	@Value("${cloud.aws.credentials.secret-key}")
	private String secretKey;
	@Value("${cloud.aws.region.static}")
	private String region;
	
	final String from="kajalrai3010@gmail.com";
	final String[] to={"kajalrai0512@gmail.com"};
	
	@Autowired
	EmailServiceImpl emailServiceImpl;
	
	@Test
	 public void sendPersonalizedTemplateEmail() {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setFromEmail(from);
		emailDetails.setToEmailList(to);
		emailDetails.setSubject("Invitation for new year celebration");
		emailDetails.setBody("The happiness of the upcoming year is a few days away. We are waiting for you to join us on the dance floor on new year’s eve. ");
		AWSResponse awsResponse = emailServiceImpl.sendPersonalizedTemplateEmail(emailDetails);
		System.out.println(awsResponse);
     }
	@Test
	 public void sendTemplateByTemplate() {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setFromEmail(from);
		emailDetails.setToEmailList(to);
		emailDetails.setSubject("Invitation for new year celebration");
		emailDetails.setBody("The happiness of the upcoming year is a few days away. We are waiting for you to join us on the dance floor on new year’s eve. ");
		AWSResponse awsResponse = emailServiceImpl.sendPersonalizedTemplateEmail(emailDetails);
		System.out.println(awsResponse);
    }
}
