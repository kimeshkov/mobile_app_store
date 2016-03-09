package com.dataart.springtraining.app.service.util;

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
