package com.cleansoftware.change;

import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.classification.SalariedClassification;
import com.cleansoftware.payment.schedule.MonthlySchedule;
import com.cleansoftware.payment.schedule.PaymentSchedule;
import com.cleansoftware.transaction.ChangeClassificationTransaction;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {
    private double salary;

    public ChangeSalariedTransaction(int empId, double salary) {
        super(empId);
        this.salary = salary;
    }


    @Override
    public PaymentClassification getClassification() {
        return new SalariedClassification(salary);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}
