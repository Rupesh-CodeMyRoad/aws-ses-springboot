package com.rg.aws.ses.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

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
	
	final String from="rupeshgaudel3@gmail.com";                                                           
	final String[] to={"rupeshgr015330@nec.edu.np"};  
	
	@Autowired
	EmailServiceImpl emailServiceImpl;
	
	@Test
	 public void sendPersonalizedTemplateEmail() {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setFromEmail(from);
		emailDetails.setToEmailList(Arrays.asList(to));
		emailDetails.setSubject("Invitation for new year celebration");
		emailDetails.setBody("The happiness of the upcoming year is a few days away. We are waiting for you to join us on the dance floor on new year’s eve. ");
		emailDetails.setTemplateName("MyTemp");
		AWSResponse awsResponse = emailServiceImpl.sendPersonalizedTemplateEmail(emailDetails);
		assertEquals(HttpStatus.OK.value(),awsResponse.getStatusCode());
		
     }
	
	
	@Test
	 public void sendAttachedEmail() {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setFromEmail(from);
		emailDetails.setToEmail("rupeshgr015330@nec.edu.np");
		emailDetails.setSubject("Invitation for new year celebration");
		emailDetails.setBody("The happiness of the upcoming year is a few days away. We are waiting for you to join us on the dance floor on new year’s eve. ");
	   String response = emailServiceImpl.sendAttachMessage(emailDetails);
		assertEquals("Mail Send Success", response);;
   }
	
	
	@Test
	 public void sendMailToVerifiedUsers() {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setFromEmail(from);
		emailDetails.setToEmailList(Arrays.asList(to));
		emailDetails.setSubject("Invitation for new year celebration");
		emailDetails.setBody("The happiness of the upcoming year is a few days away. We are waiting for you to join us on the dance floor on new year’s eve. ");
		emailDetails.setTemplateName("MyTemp");
		AWSResponse awsResponse = emailServiceImpl.sendMailToVerifiedUsers(emailDetails);
		assertEquals(HttpStatus.OK.value(),awsResponse.getStatusCode());
		
    }
	
	//@Test
	public void sendVerificationEmail (){
		String response = emailServiceImpl.sendVerificationEmail("kajalrai0512@gmail.com");   
		assertEquals("varification mail send",response);
	}
	
	//@Test
		 public void sendEmailByTemplate() {
			EmailDetails emailDetails = new EmailDetails();
			emailDetails.setFromEmail(from);
			emailDetails.setToEmailList(Arrays.asList(to));
			emailDetails.setSubject("Invitation for new year celebration");
			emailDetails.setBody("The happiness of the upcoming year is a few days away. We are waiting for you to join us on the dance floor on new year’s eve. ");
			emailDetails.setTemplateName("Temp");
			AWSResponse awsResponse = emailServiceImpl.sendPersonalizedTemplateEmail(emailDetails);
			System.out.println(awsResponse);
			assertEquals(HttpStatus.BAD_REQUEST.value(),awsResponse.getStatusCode());
	    }
}
