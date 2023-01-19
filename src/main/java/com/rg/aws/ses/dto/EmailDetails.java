package com.rg.aws.ses.dto;

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

    String[] toEmailList;

    MultipartFile attachment;
}