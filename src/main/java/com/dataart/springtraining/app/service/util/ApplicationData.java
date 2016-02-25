package com.dataart.springtraining.app.service.util;


/**
 * Created by mkim on 29/10/2015.
 */
public class ApplicationData {
    private String description;
    private Integer categoryId;

    private String name;
    private String packageName;
    private String image128;
    private String image512;

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

    public String getImage128() {
        return image128;
    }

    public void setImage128(String image128) {
        this.image128 = image128;
    }

    public String getImage512() {
        return image512;
    }

    public void setImage512(String image512) {
        this.image512 = image512;
    }
}
