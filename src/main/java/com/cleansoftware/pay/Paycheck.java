package com.cleansoftware.pay;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Paycheck {
    private Calendar payDate;
    private double grossPay;
    private double deductions;
    private double netPay;
    private Map<String, String> fields;

    public Paycheck(Calendar payDate) {
        this.payDate = payDate;
        fields = new HashMap<>();
    }
    public Calendar getPayDate() {
        return payDate;
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
