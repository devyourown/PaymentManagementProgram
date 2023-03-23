package com.cleansoftware.payment.timecard;

public class TimeCard {
    private int date;
    private double hours;

    public TimeCard(int date, double hours) {
        this.date = date;
        this.hours = hours;
    }
    public double getHours() {
        return hours;
    }

    public int getDate() {
        return date;
    }
}
