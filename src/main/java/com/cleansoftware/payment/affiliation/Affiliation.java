package com.cleansoftware.payment.affiliation;

import com.cleansoftware.pay.Paycheck;

public interface Affiliation {
    double calculateDeductions(Paycheck paycheck);
}
