package com.cleansoftware.payment.method;

import com.cleansoftware.pay.Paycheck;

public class HoldMethod implements PaymentMethod {
    @Override
    public void pay(Paycheck paycheck) {
        paycheck.setField("Disposition", "Hold");
    }
}
