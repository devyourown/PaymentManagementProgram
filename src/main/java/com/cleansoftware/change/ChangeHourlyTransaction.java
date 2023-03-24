package com.cleansoftware.change;

import com.cleansoftware.payment.classification.HourlyClassification;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.schedule.PaymentSchedule;
import com.cleansoftware.payment.schedule.WeeklySchedule;
import com.cleansoftware.transaction.ChangeClassificationTransaction;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {
    private double hourlyRate;

    public ChangeHourlyTransaction(int empId, double hourlyRate) {
        super(empId);
        this.hourlyRate = hourlyRate;
    }

    @Override
    public PaymentClassification getClassification() {
        return new HourlyClassification(hourlyRate);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }
}
