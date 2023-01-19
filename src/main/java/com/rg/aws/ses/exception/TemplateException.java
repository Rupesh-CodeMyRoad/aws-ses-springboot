package com.rg.aws.ses.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * This is the Template Not Found exception thrown when there is no template found on aws cloud
 * request being sent
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class TemplateException extends RuntimeException{

	public TemplateException(String message, Throwable cause) {
		super(message, cause);
	}
	public TemplateException(String message) {
		super(message);
	}

	
	

}
