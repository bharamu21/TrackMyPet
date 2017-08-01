package com.benayah.app.trackmypet.POJO;

public class DrawerListItems {

    private String name;
    private int imageSource;

    public DrawerListItems(int imageSource, String name) {
        this.name = name;
        this.imageSource = imageSource;
    }

    public String getName() {
        return name;
    }

    public int getImageSource() {
        return imageSource;
    }
}
