package com.guide.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadFileResponse {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
