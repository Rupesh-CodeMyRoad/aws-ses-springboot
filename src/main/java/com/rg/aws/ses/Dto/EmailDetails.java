package com.rg.aws.ses.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
@Setter
public class EmailDetails {
    String fromEmail;
    String toEmail;
    String subject;
    String body;

    MultipartFile attachment;
}
