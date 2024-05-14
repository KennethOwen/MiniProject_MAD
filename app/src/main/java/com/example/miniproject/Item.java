package com.example.miniproject;

public class Item {
    private String itemName;
    private double price;
    private int stock;
    private byte[] image; // Byte array to store image data

    public Item(String itemName, double price, int stock, byte[] image) {
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    // Getters and setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

