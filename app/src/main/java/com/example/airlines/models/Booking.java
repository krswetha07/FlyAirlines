package com.example.airlines.models;

public class Booking {
    private String from;
    private String to;
    private String date;
    private String time;
    private String flightNumber;

    public Booking(String from, String to, String date, String time, String flightNumber) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.flightNumber = flightNumber;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getFlightNumber() { return flightNumber; }
} 