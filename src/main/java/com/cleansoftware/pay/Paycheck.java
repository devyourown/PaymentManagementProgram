package com.cleansoftware.pay;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Paycheck {
    private Calendar payPeriodStartDate;
    private Calendar payPeriodEndDate;
    private double grossPay;
    private double deductions;
    private double netPay;
    private Map<String, String> fields;

    public Paycheck(Calendar periodStartDate, Calendar periodEndDate) {
        this.payPeriodStartDate = periodStartDate;
        this.payPeriodEndDate = periodEndDate;
        fields = new HashMap<>();
    }
    public Calendar getPayPeriodStartDate() {
        return payPeriodStartDate;
    }

    public Calendar getPayPeriodEndDate() {
        return payPeriodEndDate;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public String getField(String field) {
        return this.fields.get(field);
    }

    public double getDeductions() {
        return deductions;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public void setField(String field, String content) {
        this.fields.put(field, content);
    }
}
