package com.cleansoftware.transaction;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.transaction.Transaction;

public class DeleteEmployeeTransaction implements Transaction {
    private int empId;

    public DeleteEmployeeTransaction(int empId) {
        this.empId = empId;
    }

    @Override
    public void execute() {
        PayrollDatabase.getInstance().deleteEmployee(empId);
    }
}
