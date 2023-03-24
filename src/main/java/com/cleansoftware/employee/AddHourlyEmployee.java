package com.cleansoftware.employee;

import com.cleansoftware.payment.classification.HourlyClassification;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.schedule.PaymentSchedule;
import com.cleansoftware.payment.schedule.WeeklySchedule;
import com.cleansoftware.transaction.employee.AddEmployeeTransaction;

public class AddHourlyEmployee extends AddEmployeeTransaction {
    private double hourlyRate;

    public AddHourlyEmployee(int empId, String itsName, String itsAddress, double hourlyRate) {
        super(empId, itsName, itsAddress);
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
