package com.cleansoftware.change;

import com.cleansoftware.payment.classification.CommissionedClassification;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.schedule.BiweeklySchedule;
import com.cleansoftware.payment.schedule.PaymentSchedule;
import com.cleansoftware.transaction.ChangeClassificationTransaction;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {
    private double nextSalary;
    private double nextCommissionRate;

    public ChangeCommissionedTransaction(int empId, double nextSalary, double nextCommissionRate) {
        super(empId);
        this.nextSalary = nextSalary;
        this.nextCommissionRate = nextCommissionRate;
    }

    @Override
    public PaymentClassification getClassification() {
        return new CommissionedClassification(nextSalary, nextCommissionRate);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }
}
