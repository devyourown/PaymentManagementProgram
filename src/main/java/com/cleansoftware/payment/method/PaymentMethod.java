package com.cleansoftware.payment.method;

import com.cleansoftware.pay.Paycheck;

public interface PaymentMethod {
    void pay(Paycheck paycheck);
}
