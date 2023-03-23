package com.cleansoftware.payment.classification;

import com.cleansoftware.payment.receipt.SalesReceipt;

import java.util.HashMap;
import java.util.Map;

public class CommissionedClassification extends PaymentClassification {
    private double salary;
    private double commissionRate;
    private Map<Long, SalesReceipt> salesReceipts;

    public CommissionedClassification(double salary, double commissionRate) {
        this.salary = salary;
        this.commissionRate = commissionRate;
        this.salesReceipts = new HashMap<>();
    }

    public double getSalary() {
        return salary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public SalesReceipt getSalesReceipt(long date) {
        return salesReceipts.get(date);
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        salesReceipts.put(salesReceipt.getDate(), salesReceipt);
    }
}
