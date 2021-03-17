package com.guide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guide.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.guide.common.enumtype.ExceptionType;
import com.guide.common.exception.BusinessException;
import com.guide.common.model.CommonApiResponse;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.guide.common.model.CommonApiResponse.success;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(FileAPI.URL)
public class FileAPI {
    public static final String URL = "/v2/files";

    private final ObjectMapper objectMapper;

    @PostMapping(value = "/upload", consumes = "multipart/form-data;charset=UTF-8")
    public CommonApiResponse upload(@RequestPart(value = "file") MultipartFile file) throws Exception{
        Path baseDir = Paths.get("./uploadFiles/").toAbsolutePath().normalize();
        file.transferTo(new File(baseDir + "/" + file.getOriginalFilename()));
        return success();
    }

    @PostMapping(value = "/uploads", consumes = "multipart/form-data;charset=UTF-8")
    public CommonApiResponse uploads(@RequestPart(value = "files") List<MultipartFile> files) throws Exception{
        Path baseDir = Paths.get("./uploadFiles/").toAbsolutePath().normalize();
        if (!isEmpty(files)) {
            for (MultipartFile file : files) {
                file.transferTo(new File(baseDir + "/" + file.getOriginalFilename()));
            }
        }
        return success();
    }

    @PostMapping(value = "/upload/json", consumes = "multipart/form-data;charset=UTF-8")
    public CommonApiResponse uploadWithJson(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "user") String user) throws Exception{
        User requestUser = objectMapper.readValue(user, User.class);
        Path baseDir = Paths.get("./uploadFiles/").toAbsolutePath().normalize();
        file.transferTo(new File(baseDir + "/" + file.getOriginalFilename()));
        return success();
    }

    @PostMapping(value = "/uploads/json", consumes = "multipart/form-data;charset=UTF-8")
    public CommonApiResponse uploadsWithJson(@RequestPart(value = "files") List<MultipartFile> files, @RequestPart(value = "user") User user) throws Exception{
        Path baseDir = Paths.get("./uploadFiles/").toAbsolutePath().normalize();
        if (!isEmpty(files)) {
            for (MultipartFile file : files) {
                file.transferTo(new File(baseDir + "/" + file.getOriginalFilename()));
            }
        }
        return success();
    }
}