package com.example.telas_background.instanceClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassUserScreen implements Parcelable{

    private String image;
    private String id;
    private String name;

    public ClassUserScreen(String image, String id, String name) {
        this.image = image;
        this.id = id;
        this.name = name;
    }

    private ClassUserScreen(Parcel in) {
        image = in.readString();
        id = in.readString();
        name = in.readString();
    }

    public static final Parcelable.Creator<ClassUserScreen> CREATOR = new Parcelable.Creator<ClassUserScreen>() {
        @Override
        public ClassUserScreen createFromParcel(Parcel in) {
            return new ClassUserScreen(in);
        }

        @Override
        public ClassUserScreen[] newArray(int size) {
            return new ClassUserScreen[size];
        }
    };

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(id);
        dest.writeString(name);
    }
}
