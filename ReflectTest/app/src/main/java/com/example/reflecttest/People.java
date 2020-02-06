package com.example.reflecttest;

public class People {

    private String name;

    private final int age;

    public static final String TAG = People.class.getSimpleName();

    public int cityCode;

    public People() {
        age = 10;
    }

    public People(int age){
        this.age = age;
    }

    private People(String name, int age){
        this.name = name;
        this.age = age;
    }

    public int getAge(){
        return age;
    }

    private void initName(){
        name = "123";
    }
}
