package com.cleansoftware.payment.classification;

import com.cleansoftware.payment.classification.PaymentClassification;

public class SalariedClassification extends PaymentClassification {
    private double itsSalary;
    public SalariedClassification(double itsSalary) {
        this.itsSalary = itsSalary;
    }
    public double getSalary() {
        return itsSalary;
    }
}
