package com.example.phone_list_app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "phone_table")
public class Element {
    //manufacturer <String>
    //model <String>
    //android_version <int>
    //website <String>
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long element_id;

    @NonNull
    @ColumnInfo(name="manufacturer")
    private String manufacturer;

    @NonNull
    @ColumnInfo(name="model")
    private String model;

    @NonNull
    @ColumnInfo(name="android_version")
    private int android_version;

    @NonNull
    @ColumnInfo(name="website")
    private String website;

    public Element(@NonNull String manufacturer, @NonNull String model, int android_version, @NonNull String website) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.android_version = android_version;
        this.website = website;
    }

    @Ignore
    public Element(long element_id, @NonNull String manufacturer, @NonNull String model, int android_version, @NonNull String website) {
        this.element_id = element_id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.android_version = android_version;
        this.website = website;
    }

    public long getElement_id() {
        return element_id;
    }

    @NonNull
    public String getManufacturer() {
        return manufacturer;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    public int getAndroid_version() {
        return android_version;
    }

    @NonNull
    public String getWebsite() {
        return website;
    }

    public void setElement_id(long element_id) {
        this.element_id = element_id;
    }

    public void setManufacturer(@NonNull String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    public void setAndroid_version(int android_version) {
        this.android_version = android_version;
    }

    public void setWebsite(@NonNull String website) {
        this.website = website;
    }
}