package com.example.fitnessapp.classes;

public class DatabaseRef {
    private String itemId;
    private String itemData;

    public DatabaseRef() {
//        Default constructor
    }

    public DatabaseRef(String itemId, String itemData) {
        this.itemId = itemId;
        this.itemData = itemData;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemData() {
        return itemData;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }
}
