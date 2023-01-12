package com.example.tripmanager.model;

import java.io.Serializable;

public class Trip implements Serializable {
    private int id;
    private String name;
    private String destination;
    private String dateOfTrip;
    private boolean requireRisks;
    private String description;

    public Trip() {
    }

    public Trip(int id, String name, String destination, String dateOfTrip, boolean requireRisks, String description) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.dateOfTrip = dateOfTrip;
        this.requireRisks = requireRisks;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateOfTrip() {
        return dateOfTrip;
    }

    public void setDateOfTrip(String dateOfTrip) {
        this.dateOfTrip = dateOfTrip;
    }

    public boolean isRequireRisks() {
        return requireRisks;
    }

    public void setRequireRisks(boolean requireRisks) {
        this.requireRisks = requireRisks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
