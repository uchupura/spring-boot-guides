package com.guide.jpa.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("select i from Image i where TYPE(i) = :imageType")
    List<Image> findByImageType(@Param("imageType") String imageType);
}
