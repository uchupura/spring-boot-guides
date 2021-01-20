package com.guide.jpa.domain.product;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ImageTest {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private InternalImageRepository internalImageRepository;

    @Test
    @Order(1)
    public void 내부_이미지_생성() {
        InternalImage internalImage = new InternalImage("/desktop", true);
        imageRepository.save(internalImage);
    }

    @Test
    @Order(2)
    public void 외부_이미지_생성() {
        ExternalImage externalImage = new ExternalImage("/desktop", "uchupura");
        imageRepository.save(externalImage);
    }

    @Test
    @Order(3)
    public void 이미지_전체_검색() {
        List<Image> images = imageRepository.findAll();
        images.forEach(image -> {
            if (image instanceof InternalImage) {
//                System.out.println("Internal : " + image.getId());
                InternalImage in = (InternalImage) image;
//                System.out.println(in.isWatermark());
            }
            if (image instanceof ExternalImage) {
//                System.out.println("External : " + image.getId());
                ExternalImage externalImage = (ExternalImage) image;
//                System.out.println(externalImage.getMadeBy());
            }
        });
    }

    @Test
    @Order(4)
    public void 내부_이미지_검색() {
        List<InternalImage> internalImages = internalImageRepository.findAll();
        internalImages.forEach(i -> System.out.println(i.getPath()));
    }
}