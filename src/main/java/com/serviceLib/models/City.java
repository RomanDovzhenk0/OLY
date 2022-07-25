package com.serviceLib.models;

public class City {
    private int id;
    private String name;
    private String region;

    public City(int id, String name, String region) {
        this.id = id;
        this.name = name;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }
}
