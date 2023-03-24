package com.cleansoftware.transaction;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.Employee;

public abstract class ChangeEmployeeTransaction implements Transaction {
    private int empId;

    public ChangeEmployeeTransaction(int empId) {
        this.empId = empId;
    }

    public abstract void change(Employee e);

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        if (e != null)
            change(e);
    }
}
