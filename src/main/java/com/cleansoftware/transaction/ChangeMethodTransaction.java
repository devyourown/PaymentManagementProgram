package com.cleansoftware.transaction;

import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.method.PaymentMethod;

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
