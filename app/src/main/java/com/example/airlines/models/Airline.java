package com.example.airlines.models;

public class Airline {
    private String name;
    private int logoResId;
    private String websiteUrl;

    public Airline(String name, int logoResId, String websiteUrl) {
        this.name = name;
        this.logoResId = logoResId;
        this.websiteUrl = websiteUrl;
    }

    public String getName() {
        return name;
    }

    public int getLogoResId() {
        return logoResId;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }
} 