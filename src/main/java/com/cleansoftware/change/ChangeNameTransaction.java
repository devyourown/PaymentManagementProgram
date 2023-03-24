package com.cleansoftware.change;

import com.cleansoftware.employee.Employee;
import com.cleansoftware.transaction.ChangeEmployeeTransaction;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {
    private String nextName;

    public ChangeNameTransaction(int empId, String nextName) {
        super(empId);
        this.nextName = nextName;
    }

    @Override
    public void change(Employee e) {
        e.setItsName(nextName);
    }
}
