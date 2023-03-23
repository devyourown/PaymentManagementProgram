package com.cleansoftware.payment.affiliation;

public class ServiceCharge {
    private long date;
    private double charge;
    public ServiceCharge(long date, double charge) {
        this.date = date;
        this.charge = charge;
    }

    public double getAmount() {
        return charge;
    }
}
