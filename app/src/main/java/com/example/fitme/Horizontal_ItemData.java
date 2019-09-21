package com.example.fitme;

import java.util.ArrayList;

public class Horizontal_ItemData extends ArrayList<Horizontal_ItemData> {

    String imageView_reviewcard_img;


    public Horizontal_ItemData(String imageView_reviewcard_img) {

        this.imageView_reviewcard_img = imageView_reviewcard_img;

    }

    public String getImageView_reviewcard_img() {
        return imageView_reviewcard_img;
    }

    public void setImageView_reviewcard_img(String imageView_reviewcard_img) {
        this.imageView_reviewcard_img = imageView_reviewcard_img;
    }
}
