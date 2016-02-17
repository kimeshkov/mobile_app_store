package com.dataart.springtraining.app.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by mkim on 13/10/2015.
 */

@Entity
@Table(
        name = "application",
        uniqueConstraints = @UniqueConstraint(columnNames = {"package_name"})
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "package_name")
    private String packageName;

    @OneToOne
    @JoinColumn(name = "picture_128")
    private FileStoreData picture128;

    @OneToOne
    @JoinColumn(name = "picture_512")
    private FileStoreData picture512;

    @OneToOne
    @JoinColumn(name = "zip_file")
    private FileStoreData zipFile;

    @Column(name = "downloads")
    private Integer downloads;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public FileStoreData getPicture128() {
        return picture128;
    }

    public void setPicture128(FileStoreData picture128) {
        this.picture128 = picture128;
    }

    public FileStoreData getPicture512() {
        return picture512;
    }

    public void setPicture512(FileStoreData picture512) {
        this.picture512 = picture512;
    }

    public FileStoreData getZipFile() {
        return zipFile;
    }

    public void setZipFile(FileStoreData zipFile) {
        this.zipFile = zipFile;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(packageName, that.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, packageName);
    }
}
