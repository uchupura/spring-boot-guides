package com.guide.jpa.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("II")
public class InternalImage extends Image {

    private boolean watermark;
    public InternalImage(String path, boolean watermark) {
        super(path);
        this.watermark = watermark;
    }
    @Override
    public String getUrl() {
        return "/images/original/" + getPath();
    }

    @Override
    public boolean hasThumbnail() {
        return true;
    }

    @Override
    public String getThumbnailUrl() {
        return "/images/thumbnail/" + getPath();
    }
}
