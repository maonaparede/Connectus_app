package com.example.telas_background.instanceClasses;

public class ClassChatMsg {

    private String name;
    private String msg;
    private String id;

    public ClassChatMsg(String name, String msg, String id) {
        this.name = name;
        this.msg = msg;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public String getId() {
        return id;
    }
}
