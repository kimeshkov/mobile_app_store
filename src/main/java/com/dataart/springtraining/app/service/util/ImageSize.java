package com.dataart.springtraining.app.service.util;

/**
 * Created by kimeshkov on 17.02.2016.
 */
public enum ImageSize {
    IMAGE_128(128), IMAGE_512(512);

    private int size;

    ImageSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
