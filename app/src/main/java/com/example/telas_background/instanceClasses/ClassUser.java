package com.example.telas_background.instanceClasses;

public class ClassUser {

    private String image;
    private String id;
    private String name;

    public ClassUser(String image, String id, String name) {
        this.image = image;
        this.id = id;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
