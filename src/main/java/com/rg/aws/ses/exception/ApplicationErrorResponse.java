package com.rg.aws.ses.exception;


import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import org.springframework.http.HttpStatus;

/**
 * Represents a JSON response for all the caught exceptions
 */
@Generated
@Builder
@Data
public class ApplicationErrorResponse {

    private String errorMessage;
    private HttpStatus errorCode;
    private String customMessage;

}
