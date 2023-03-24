package com.cleansoftware.change;

import com.cleansoftware.payment.method.HoldMethod;
import com.cleansoftware.payment.method.PaymentMethod;
import com.cleansoftware.transaction.ChangeMethodTransaction;

public class ChangeHoldTransaction extends ChangeMethodTransaction {

    public ChangeHoldTransaction(int empId) {
        super(empId);
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return new HoldMethod();
    }
}
