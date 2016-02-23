package com.dataart.springtraining.app.service.util;


/**
 * Created by mkim on 29/10/2015.
 */
public class ApplicationData {
    private String description;
    private Integer categoryId;

    private String name;
    private String packageName;
    private String picture128;
    private String picture512;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
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

    public String getPicture128() {
        return picture128;
    }

    public void setPicture128(String picture128) {
        this.picture128 = picture128;
    }

    public String getPicture512() {
        return picture512;
    }

    public void setPicture512(String picture512) {
        this.picture512 = picture512;
    }
}
