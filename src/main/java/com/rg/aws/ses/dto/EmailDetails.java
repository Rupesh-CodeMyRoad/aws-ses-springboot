package com.rg.aws.ses.dto;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter@ToString
@Setter
public class EmailDetails {
    String fromEmail;
    String toEmail;
    String subject;
    String body;

    List<String> toEmailList;
    String templateName;
    Set<MultipartFile> attachment;
}
