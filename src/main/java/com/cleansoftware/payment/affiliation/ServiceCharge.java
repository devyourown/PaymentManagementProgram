package com.cleansoftware.payment.affiliation;

import java.util.Calendar;

public class ServiceCharge {
    private Calendar date;
    private double charge;
    public ServiceCharge(Calendar date, double charge) {
        this.date = date;
        this.charge = charge;
    }

    public double getAmount() {
        return charge;
    }

    public Calendar getDate() {
        return date;
    }
}
