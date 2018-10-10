package com.example.user.graduationproject.Bjelasnica.Utils;

public class GalleryImageHolder {
    private final int imageId;

    public static GalleryImageHolder of(final int imageId) {
        return new GalleryImageHolder(imageId);
    }

    private GalleryImageHolder(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }
}
