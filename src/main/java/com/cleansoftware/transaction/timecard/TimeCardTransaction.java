package com.cleansoftware.transaction.timecard;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.classification.HourlyClassification;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.timecard.TimeCard;
import com.cleansoftware.transaction.Transaction;

public class TimeCardTransaction implements Transaction {
    private int date;
    private double hours;
    private int empId;

    public TimeCardTransaction(int date, double hours, int empId) {
        this.date = date;
        this.hours = hours;
        this.empId = empId;
    }
    @Override
    public void execute() {
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        if (e == null)
            throw new RuntimeException("No Employee");
        HourlyClassification hc = (HourlyClassification) e.getPaymentClassification();
        if (hc == null)
            throw new RuntimeException("Not Hourly Classification.");
        hc.addTimeCard(new TimeCard(date, hours));
    }
}
