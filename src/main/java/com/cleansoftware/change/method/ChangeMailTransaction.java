package com.cleansoftware.change.method;

import com.cleansoftware.payment.method.MailMethod;
import com.cleansoftware.payment.method.PaymentMethod;
import com.cleansoftware.transaction.change.ChangeMethodTransaction;

public class ChangeMailTransaction extends ChangeMethodTransaction {

    public ChangeMailTransaction(int empId) {
        super(empId);
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return new MailMethod();
    }
}
