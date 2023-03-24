package com.cleansoftware.payment.receipt;

import java.util.Calendar;

public class SalesReceipt {
    private Calendar date;
    private double amount;

    public SalesReceipt(Calendar date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public Calendar getDate() {
        return date;
    }
}
