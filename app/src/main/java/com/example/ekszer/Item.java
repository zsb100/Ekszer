package com.example.ekszer;

public class Item {

    private String name;
    private String description;
    private int imageUrl;
    private String price;
    private float rating;


    public Item(String name, String description, int imageRes, String price, float rating) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageRes;
        this.price = price;
        this.rating = rating;
    }

    public Item(){

        this.name = "";
        this.description = "";
        this.imageUrl = 0;
        this.price = "";
        this.rating = 0.0f;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    public void setImageUrl(int imageRes) {
        this.imageUrl = imageRes;
    }
}
