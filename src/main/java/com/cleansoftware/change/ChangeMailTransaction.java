package com.cleansoftware.change;

import com.cleansoftware.payment.method.MailMethod;
import com.cleansoftware.payment.method.PaymentMethod;
import com.cleansoftware.transaction.ChangeMethodTransaction;

public class ChangeMailTransaction extends ChangeMethodTransaction {

    public ChangeMailTransaction(int empId) {
        super(empId);
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return new MailMethod();
    }
}
