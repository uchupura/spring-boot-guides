package com.guide.jpa.domain.product;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "image_type")
public abstract class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_path")
    private String path;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    public Image(String path) {
        this.path = path;
        this.uploadTime = LocalDateTime.now();
    }

    public abstract String getUrl();
    public abstract boolean hasThumbnail();
    public abstract String getThumbnailUrl();
}