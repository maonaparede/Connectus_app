package com.example.telas_background;

import androidx.annotation.NonNull;

public class CleanString extends Object {

    public static String clean(Object object){
       return object == null? "v" : object.toString();
    }
}
