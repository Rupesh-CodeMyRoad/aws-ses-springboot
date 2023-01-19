package com.rg.aws.ses.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ENUM represents all Response code we are going to receive from AWS.
 */
@Getter
@AllArgsConstructor
public enum AWSErrorCode {

    ACCOUNT_SENDING_PAUSED(400, "AccountSendingPaused", "Email sending is disabled for your entire Amazon SES account."),
    CONFIGURATION_SET_DOES_NOT_EXIST(400, "ConfigurationSetDoesNotExist", "Configuration set does not exist."),
    CONFIGURATION_SET_SENDING_PAUSED(400, "ConfigurationSetSendingPaused", "Email sending is disabled for the configuration set."),
    MAIL_FROM_DOMAIN_NOT_VERIFIED(400, "MailFromDomainNotVerified", "Amazon SES could not read the MX record required to use the specified MAIL FROM domain."),
    MESSAGE_REJECTED(400, "MessageRejected", "Action failed, and the message could not be sent."),
    TEMPLATE_DOES_NOT_EXIST(400, "TemplateDoesNotExist", "Template object you specified does not exist in your Amazon SES account."),
    ACCESS_DENIED_EXCEPTION(400, "AccessDeniedException", "You do not have sufficient access to perform this action."),
    VALIDATION_ERROR(400, "ValidationError", "The input fails to satisfy the constraints specified by an AWS service.");

    private int code;
    private String errorCode;
    private String description;

    /**
     * Get Response code from code
     *
     * @param code code value
     * @return ResponseCode
     */
    public static AWSErrorCode getResponseCode(int code) {
        for (AWSErrorCode rc : values()) {
            if (rc.getCode() == code) {
                return rc;
            }
        }
        throw new IllegalArgumentException("Not found any response code for " + code);

    }
}
