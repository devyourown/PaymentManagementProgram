package com.cleansoftware.transaction;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.affiliation.NoAffiliation;
import com.cleansoftware.payment.method.HoldMethod;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.method.PaymentMethod;
import com.cleansoftware.payment.schedule.PaymentSchedule;

public abstract class AddEmployeeTransaction implements Transaction{
    protected int empId;
    protected String itsName;
    protected String itsAddress;

    public abstract PaymentClassification getClassification();

    public abstract PaymentSchedule getSchedule();

    public AddEmployeeTransaction(int empId, String itsName, String itsAddress) {
        this.empId = empId;
        this.itsName = itsName;
        this.itsAddress = itsAddress;
    }

    @Override
    public void execute() {
        PaymentClassification pc = getClassification();
        PaymentSchedule ps = getSchedule();
        PaymentMethod pm = new HoldMethod();
        Employee e = new Employee(empId, itsName, itsAddress);
        e.setPaymentClassification(pc);
        e.setPaymentSchedule(ps);
        e.setPaymentMethod(pm);
        e.setAffiliation(new NoAffiliation());
        PayrollDatabase.getInstance().addEmployee(empId, e);
    }
}
