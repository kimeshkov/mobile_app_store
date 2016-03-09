package com.dataart.springtraining.app.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private int downloads;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "application_rate",
            joinColumns = @JoinColumn(name = "application_id")
    )
    private List<Rate> rates;

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

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public void addRate(Rate rate) {
        rates.add(rate);
    }

    public int getAverageRate() {
        return rates.size() == 0 ? 0 : calculateAverageRate();

    }

    private int calculateAverageRate() {
        int sum = 0;
        for (Rate rate : rates) {
            sum += rate.getValue();
        }
        return sum / rates.size();
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
