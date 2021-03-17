package com.guide.controller;

import com.guide.common.BaseTest;
import com.guide.model.User;
import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FileAPITest extends BaseTest {
    private final String RESOURCE = "File/";

    @Test
    public void 이미지_JSON_업로드() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File image = new File(classLoader.getResource("files/test_img.jpeg").getFile());
        FileInputStream fileInputStream = new FileInputStream(image);
        MockMultipartFile imageFile = new MockMultipartFile("file", "test_img.jpeg", "image/jpeg", fileInputStream);

        User user = new User("uchupura", "password", "name");
        MockMultipartFile userData = new MockMultipartFile("user", "", "application/json", objectMapper.writeValueAsString(user).getBytes());

        this.mockMvc
                .perform(fileUpload("/v2/files/upload/json")
                        .file(imageFile)
                        .file(userData)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(RESOURCE + "{method-name}",
                                requestParts(
                                        partWithName("file").description("업로드 할 이미지")
                                        , partWithName("user").description("이미지 내용")
                                )
                        )
                )
                .andReturn().getResponse();
    }

    @Test
    public void 이미지여러개_JSON_업로드() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File image = new File(classLoader.getResource("files/test_img.jpeg").getFile());
        FileInputStream fileInputStream = new FileInputStream(image);
        MockMultipartFile imageFile = new MockMultipartFile("files", "test_img.jpeg", "image/jpeg", fileInputStream);

        File image2 = new File(classLoader.getResource("files/test_img2.jpeg").getFile());
        FileInputStream fileInputStream2 = new FileInputStream(image2);
        MockMultipartFile imageFile2 = new MockMultipartFile("files", "test_img2.jpeg", "image/jpeg", fileInputStream2);

        User user = new User("uchupura", "password", "name");
        MockMultipartFile userData = new MockMultipartFile("user", "", "application/json", objectMapper.writeValueAsString(user).getBytes());

        this.mockMvc
                .perform(fileUpload("/v2/files/uploads/json")
                        .file(imageFile)
                        .file(imageFile2)
                        .file(userData)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(RESOURCE + "{method-name}",
                        requestParts(
                                partWithName("files").description("업로드 할 이미지 리스트")
                                , partWithName("user").description("이미지 내용")
                        )
                        )
                )
                .andReturn().getResponse();
    }
}
