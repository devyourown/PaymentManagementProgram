package com.cleansoftware.transaction.change;

import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.method.PaymentMethod;
import com.cleansoftware.transaction.change.ChangeEmployeeTransaction;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {

    public ChangeMethodTransaction(int empId) {
        super(empId);
    }

    public abstract PaymentMethod getPaymentMethod();

    @Override
    public void change(Employee e) {
        e.setPaymentMethod(getPaymentMethod());
    }
}
