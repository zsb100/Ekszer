package com.example.ekszer;

public class Item {

    private String name;
    private String description;
    private String imageUrl;
    private String price;
    private float rating;


    public Item(String name, String description, String imageRes, String price, float rating) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageRes;
        this.price = price;
        this.rating = rating;
    }

    public Item(){

        this.name = "";
        this.description = "";
        this.imageUrl = "";
        this.price = "";
        this.rating = 0.0f;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    public void setImageUrl(String imageRes) {
        this.imageUrl = imageRes;
    }
}
