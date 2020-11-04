package com.guide.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Setter @Getter
public class UploadFileRequest {

    private String name;
    private String email;
    private MultipartFile file;

    @Builder
    public UploadFileRequest(String name, String email, MultipartFile file) {
        this.name = name;
        this.email = email;
        this.file = file;
    }
}
