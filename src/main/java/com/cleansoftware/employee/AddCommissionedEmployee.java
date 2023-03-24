package com.cleansoftware.employee;

import com.cleansoftware.payment.classification.CommissionedClassification;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.schedule.BiweeklySchedule;
import com.cleansoftware.payment.schedule.PaymentSchedule;
import com.cleansoftware.transaction.employee.AddEmployeeTransaction;

public class AddCommissionedEmployee extends AddEmployeeTransaction {

    private double salary;
    private double commissionRate;

    public AddCommissionedEmployee(int empId, String itsName, String itsAddress,
                                   double salary, double commissionRate) {
        super(empId, itsName, itsAddress);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }


    @Override
    public PaymentClassification getClassification() {
        return new CommissionedClassification(salary, commissionRate);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }
}
