package com.cleansoftware.change;

import com.cleansoftware.payment.method.DirectMethod;
import com.cleansoftware.payment.method.PaymentMethod;
import com.cleansoftware.transaction.ChangeMethodTransaction;

public class ChangeDirectTransaction extends ChangeMethodTransaction {

    public ChangeDirectTransaction(int empId) {
        super(empId);
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return new DirectMethod();
    }
}
