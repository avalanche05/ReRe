package com.example.restaurantrent;

import android.widget.Button;

import java.util.ArrayList;

public class Restaurant {
    private long id;
    private long idOwners;
    private String name;
    private String address;

    private ArrayList<Button> tables = new ArrayList<Button>();

    public Restaurant() {
    }

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Restaurant(long idOwners, String name, String address){
        this.idOwners = idOwners;
        this.name = name;
        this.address = address;
    }

    public Restaurant(long id, long idOwners, String name, String address) {
        this.id = id;
        this.idOwners = idOwners;
        this.name = name;
        this.address = address;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Button> getTables() {
        return tables;
    }

    public long getIdOwners() {
        return idOwners;
    }

    public void setIdOwners(long idOwners) {
        this.idOwners = idOwners;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTables(ArrayList<Button> tables) {
        this.tables = tables;
    }

    public void setTables(int i, Button table) {
        this.tables.set(i,table);
    }
    public void addTables(Button table){
        this.tables.add(table);
    }
}
