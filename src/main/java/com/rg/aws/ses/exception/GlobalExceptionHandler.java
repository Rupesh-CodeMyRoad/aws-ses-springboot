package com.rg.aws.ses.exception;


import com.rg.aws.ses.utils.AWSResponseCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Class will handle the various Exceptions that may be experienced in
 * application.
 */
@AllArgsConstructor
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Catches a TemplateNotFoundException
     *
     * @param exception represents the caught exception
     * @return AWS error response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TemplateException.class)
    public ResponseEntity<ApplicationErrorResponse> templateNotFoundException(TemplateException exception) {
        log.info("Template Not found Error: {}", exception.getMessage());
        log.error("TemplateException", exception);
        return new ResponseEntity<>(
                ApplicationErrorResponse.builder()
                        .errorMessage(exception.getMessage())
                        .errorCode(HttpStatus.valueOf(AWSResponseCode.TEMPLATE_DOES_NOT_EXIST.getCode()))
                        .customMessage("Template Doesn't Exist on AWS.")
                        .build(), HttpStatus.BAD_REQUEST);

    }

}
