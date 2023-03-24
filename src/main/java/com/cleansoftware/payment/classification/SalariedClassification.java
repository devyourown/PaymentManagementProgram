package com.cleansoftware.payment.classification;

import com.cleansoftware.pay.Paycheck;
import com.cleansoftware.payment.classification.PaymentClassification;

public class SalariedClassification implements PaymentClassification {
    private double itsSalary;
    public SalariedClassification(double itsSalary) {
        this.itsSalary = itsSalary;
    }
    public double getSalary() {
        return itsSalary;
    }

    @Override
    public double calculatePay(Paycheck paycheck) {
        return getSalary();
    }
}
