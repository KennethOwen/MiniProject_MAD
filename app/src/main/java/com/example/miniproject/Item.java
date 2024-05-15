package com.example.miniproject;

public class Item {
    private int itemId;
    private String itemName;
    private double price;
    private int stock;
    private byte[] image; // Byte array to store image data

    public Item(int itemId,String itemName, double price, int stock, byte[] image) {
        this.itemId = itemId;
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

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}

