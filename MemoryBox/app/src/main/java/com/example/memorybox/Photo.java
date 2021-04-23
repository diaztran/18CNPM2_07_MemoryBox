package com.example.memorybox;

import java.time.LocalDate;

public class Photo {
    private String path;
    private String thumb;
    public Photo(String path, String thumb) {
        this.path = path;
        this.thumb = thumb;
    }

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



//    public LocalDate getTimeCreate() {
//        return timeCreate;
//    }
//
//    public void setTimeCreate(LocalDate timeCreate) {
//        this.timeCreate = timeCreate;
//    }


}
