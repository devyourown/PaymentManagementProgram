package com.cleansoftware.payment.classification;

import com.cleansoftware.pay.Paycheck;
import com.cleansoftware.payment.UtilDate;
import com.cleansoftware.payment.receipt.SalesReceipt;

import java.util.*;

public class CommissionedClassification implements PaymentClassification {
    private double salary;
    private double commissionRate;
    private Map<Calendar, List<SalesReceipt>> salesReceipts;

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

    public List<SalesReceipt> getSalesReceipt(Calendar date) {
        return salesReceipts.get(date);
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        if (salesReceipts.get(salesReceipt.getDate()) == null)
            salesReceipts.put(salesReceipt.getDate(), new ArrayList<>());
        salesReceipts.get(salesReceipt.getDate()).add(salesReceipt);
    }

    @Override
    public double calculatePay(Paycheck paycheck) {
        double result = getSalary();
        for (List<SalesReceipt> bunchOfSR : salesReceipts.values()) {
            for (SalesReceipt sr : bunchOfSR) {
                if (UtilDate.isInPayPeriod(sr.getDate(), paycheck))
                    result += calculateCommission(sr);
            }
        }
        return result;
    }
    private double calculateCommission(SalesReceipt sr) {
        return sr.getAmount() * getCommissionRate() * 0.01;
    }
}
