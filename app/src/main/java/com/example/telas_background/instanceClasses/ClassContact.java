package com.example.telas_background.instanceClasses;

public class ClassContact extends ClassUserScreen {

    private String path;
    private String lastMsg;

    public ClassContact(String image, String id, String name, String path, String lastMsg) {
        super(image, id, name);
        this.path = path;
        this.lastMsg = lastMsg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLastMsg() {
        return lastMsg;
    }
}
