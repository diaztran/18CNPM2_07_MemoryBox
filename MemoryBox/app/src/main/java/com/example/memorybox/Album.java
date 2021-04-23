package com.example.memorybox;

public class Album {
    private String name;
    // Một album lúc nào cũng phải có ít nhất một ảnh bên trong, nếu không
    private String firstImageContainedPath;

    public Album(String name, String firstImageContainedPath) {
        this.name = name;
        this.firstImageContainedPath = firstImageContainedPath;
    }

    public String getName() {
        return name;
    }

    public String getFirstImageContainedPath() {
        return firstImageContainedPath;
    }
}
