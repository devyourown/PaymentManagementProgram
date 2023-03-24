package com.cleansoftware.payment.classification;

import com.cleansoftware.pay.Paycheck;

import java.util.Calendar;

public interface PaymentClassification {
    double calculatePay(Paycheck paycheck);
}
