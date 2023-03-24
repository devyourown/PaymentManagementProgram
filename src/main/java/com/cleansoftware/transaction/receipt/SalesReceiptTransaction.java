package com.cleansoftware.transaction.receipt;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.classification.CommissionedClassification;
import com.cleansoftware.payment.receipt.SalesReceipt;
import com.cleansoftware.transaction.Transaction;

public class SalesReceiptTransaction implements Transaction {
    private long date;
    private double amount;
    private int empId;

    public SalesReceiptTransaction(long date, double amount, int empId) {
        this.date = date;
        this.amount = amount;
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        if (e == null)
            throw new RuntimeException("No Employee.");
        CommissionedClassification cc = (CommissionedClassification) e.getPaymentClassification();
        cc.addSalesReceipt(new SalesReceipt(date, amount));
    }
}
