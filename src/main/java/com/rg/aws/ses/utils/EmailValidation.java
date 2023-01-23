package com.rg.aws.ses.utils;

import com.rg.aws.ses.dto.EmailDetails;
import com.rg.aws.ses.exception.EmailValidationException;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class EmailValidation {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static void validateEmails(EmailDetails emailDetails) {
        if (EmailValidation.validate(emailDetails.getFromEmail())) {
            log.error("From mail : {}", emailDetails.getFromEmail());
            if (emailDetails.getToEmailList().isEmpty()) {
                if (EmailValidation.validate(emailDetails.getToEmail())) {
                    log.error("To mail : {}", emailDetails.getToEmail());
                } else {
                    throw new EmailValidationException("Email address Mismatch in to mail : " + emailDetails.getToEmail());
                }
            } else {
                for (String email : emailDetails.getToEmailList()) {
                    if (EmailValidation.validate(email)) {
                        log.error("To mail : {}", email);
                    } else {
                        throw new EmailValidationException("Email address Mismatch in to mail : " + email);
                    }
                }
            }
        } else {
            throw new EmailValidationException("Email address Mismatch in from mail : " + emailDetails.getFromEmail());
        }
    }
}
