package com.rg.aws.ses.exception;


import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ApplicationErrorResponse {
	
	

	private String errorMessage;

    private HttpStatus errorCode;

    private String customMessage;

}
