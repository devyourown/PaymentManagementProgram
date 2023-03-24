package com.cleansoftware.change.personal;

import com.cleansoftware.employee.Employee;
import com.cleansoftware.transaction.change.ChangeEmployeeTransaction;

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
