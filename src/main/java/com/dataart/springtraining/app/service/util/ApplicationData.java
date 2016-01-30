package com.dataart.springtraining.app.service.util;


import java.util.Optional;

/**
 * Created by mkim on 29/10/2015.
 */
public class ApplicationData {
    private String description;
    private int categoryId;

    private String name;
    private String packageName;
    private Optional<String> picture128 = Optional.empty();
    private Optional<String> picture512 = Optional.empty();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Optional<String> getPicture128() {
        return picture128;
    }

    public void setPicture128(Optional<String> picture128) {
        this.picture128 = picture128;
    }

    public Optional<String> getPicture512() {
        return picture512;
    }

    public void setPicture512(Optional<String> picture512) {
        this.picture512 = picture512;
    }
}
