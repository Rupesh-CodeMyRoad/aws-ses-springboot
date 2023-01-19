package com.rg.aws.ses.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public class TemplateException extends RuntimeException{

	public TemplateException() {
		super();
	}

	public TemplateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TemplateException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateException(String message) {
		super(message);
	}

	public TemplateException(Throwable cause) {
		super(cause);
	}
	
	

}
