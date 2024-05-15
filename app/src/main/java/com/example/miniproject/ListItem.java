package com.example.miniproject;

public class ListItem {
    private int id;
    private String itemName;

    // Constructor for ID and name only
    public ListItem(int id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
