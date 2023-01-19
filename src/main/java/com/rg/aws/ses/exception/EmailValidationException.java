package com.rg.aws.ses.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * This is the Template Not Found exception thrown when there is no template found on aws cloud
 * request being sent
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class EmailValidationException extends RuntimeException{

	public EmailValidationException(String message, Throwable cause) {
		super(message, cause);
	}
	public EmailValidationException(String message) {
		super(message);
	}

	
	

}
