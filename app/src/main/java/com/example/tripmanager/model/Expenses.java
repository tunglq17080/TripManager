package com.example.tripmanager.model;

import java.io.Serializable;

public class Expenses implements Serializable {
    private int id;
    private String type;
    private String amount;
    private String time;

    public Expenses(int id, String type, String amount, String time) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
