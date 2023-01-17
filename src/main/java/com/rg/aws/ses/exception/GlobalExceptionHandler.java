package com.rg.aws.ses.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApplicationErrorResponse> mailExceptionHandler(BusinessException be)
	{
		log.error("email could n't be process "+be.getStackTrace());
	    return	new ResponseEntity<>(ApplicationErrorResponse.builder().errorMessage(be.getMessage()).errorCode(HttpStatus.INTERNAL_SERVER_ERROR).customMessage(be.getMessage()).build(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
