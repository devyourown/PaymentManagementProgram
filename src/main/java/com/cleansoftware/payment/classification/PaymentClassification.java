package com.cleansoftware.payment.classification;

import com.cleansoftware.pay.Paycheck;

public interface PaymentClassification {
    double calculatePay(Paycheck paycheck);
}
