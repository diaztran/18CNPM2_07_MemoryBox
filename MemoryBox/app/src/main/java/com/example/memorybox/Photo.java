package com.example.memorybox;

import java.time.LocalDate;

public class Photo {
    private String path;
    private String thumb;
    public Photo(String path, String thumb) {
        this.path = path;
        this.thumb = thumb;
    }

    Boolean isSelected = false;

    public Photo() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

//    public LocalDate getTimeCreate() {
//        return timeCreate;
//    }
//
//    public void setTimeCreate(LocalDate timeCreate) {
//        this.timeCreate = timeCreate;
//    }


}
