package com.cleansoftware.payment.classification;

import com.cleansoftware.pay.Paycheck;

import java.util.Calendar;

public interface PaymentClassification {
    double calculatePay(Paycheck paycheck);

    public default boolean isInPayPeriod(Calendar theDate, Paycheck pc) {
        return (pc.getPayPeriodStartDate().before(theDate)) &&
                (theDate.before(pc.getPayPeriodEndDate()));
    }
}
