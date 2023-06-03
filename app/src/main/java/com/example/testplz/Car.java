package com.example.testplz;

import android.graphics.drawable.Drawable;

public class Car {
    private Drawable image;
    private String name;
    private int count;

    public Car(Drawable image, String name, int count) {
        this.image = image;
        this.name = name;
        this.count = count;
    }

    public Drawable getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}

