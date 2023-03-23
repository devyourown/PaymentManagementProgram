package com.cleansoftware.employee;

import com.cleansoftware.payment.schedule.MonthlySchedule;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.schedule.PaymentSchedule;
import com.cleansoftware.payment.classification.SalariedClassification;
import com.cleansoftware.transaction.AddEmployeeTransaction;

public class AddSalariedEmployee extends AddEmployeeTransaction {
    private double salary;
    public AddSalariedEmployee(int empId, String itsName, String itsAddress, double salary) {
        super(empId, itsName, itsAddress);
        this.salary = salary;
    }

    @Override
    public PaymentClassification getClassification() {
        return new SalariedClassification(this.salary);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}
