package com.example.reflecttest.annotation;

import android.util.Log;

public class Car {

    private static final String TAG = "AnnotationCar";

    @MyAnnotation
    public void run(){
        Log.i(TAG, "run: ");
    }

    @MyAnnotation(value = {"smallCar, bigCar"})
    public void defaultCar(String name, int money){
        Log.i(TAG, "defaultCar: " + name);
    }


}
