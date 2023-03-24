package com.cleansoftware.payment.affiliation;

import com.cleansoftware.pay.Paycheck;

public class NoAffiliation implements Affiliation {
    @Override
    public double calculateDeductions(Paycheck paycheck) {
        return 0;
    }
}
