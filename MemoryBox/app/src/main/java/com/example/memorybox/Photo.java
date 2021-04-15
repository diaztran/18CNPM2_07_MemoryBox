package com.example.memorybox;

import java.time.LocalDate;

public class Photo {
    private String id;
    private String name;
    private int photoResource;

    public Photo(String id, String name, int photoResource) {
        this.id = id;
        this.name = name;
//        this.timeCreate = timeCreate;
        this.photoResource = photoResource;
    }

    public Photo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public LocalDate getTimeCreate() {
//        return timeCreate;
//    }
//
//    public void setTimeCreate(LocalDate timeCreate) {
//        this.timeCreate = timeCreate;
//    }

    public int getPhotoResource() {
        return photoResource;
    }

    public void setPhotoResource(int photoResource) {
        this.photoResource = photoResource;
    }
}
