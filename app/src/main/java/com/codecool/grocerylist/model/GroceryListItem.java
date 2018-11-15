package com.codecool.grocerylist.model;

import java.util.UUID;

public class GroceryListItem {

    private String name;
    private int quantity;
    private int price;
    private String id;
    private String deviceID;

    public GroceryListItem(String name, int quantity, int price, String deviceID, String id) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.id = id;
        this.deviceID = deviceID;


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceID() {
        return deviceID;
    }
}
