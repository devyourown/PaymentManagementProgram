package com.cleansoftware.transaction;

import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.schedule.PaymentSchedule;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {

    public ChangeClassificationTransaction(int empId) {
        super(empId);
    }

    public abstract PaymentClassification getClassification();
    public abstract PaymentSchedule getSchedule();

    @Override
    public void change(Employee e) {
        e.setPaymentClassification(getClassification());
        e.setPaymentSchedule(getSchedule());
    }
}
