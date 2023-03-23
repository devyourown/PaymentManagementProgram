package com.cleansoftware.payment.receipt;

public class SalesReceipt {
    private long date;
    private double amount;

    public SalesReceipt(long date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public long getDate() {
        return date;
    }
}
