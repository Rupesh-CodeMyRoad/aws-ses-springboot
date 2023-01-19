package com.rg.aws.ses.dto;

import lombok.*;

import java.util.Map;

/**
 * Represents response for all AWS request
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AWSResponse {
    String messageId;
    String awsRequestId;
    int statusCode;
}
