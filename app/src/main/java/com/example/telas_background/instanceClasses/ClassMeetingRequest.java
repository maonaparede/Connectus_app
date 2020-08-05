package com.example.telas_background.instanceClasses;

public class ClassMeetingRequest {

    private String title;
    private String ownerId;
    private String local;
    private String day;
    private String hour;
    private String image;
    private String name;

    public ClassMeetingRequest(String title, String ownerId, String local, String day, String hour, String image, String name) {
        this.title = title;
        this.ownerId = ownerId;
        this.local = local;
        this.day = day;
        this.hour = hour;
        this.image = image;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getLocal() {
        return local;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
