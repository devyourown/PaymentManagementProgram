package com.cleansoftware.change;

import com.cleansoftware.employee.Employee;
import com.cleansoftware.transaction.ChangeEmployeeTransaction;

public class ChangeAddressTransaction extends ChangeEmployeeTransaction {
    private String nextAddress;

    public ChangeAddressTransaction(int empId, String nextAddress) {
        super(empId);
        this.nextAddress = nextAddress;
    }

    @Override
    public void change(Employee e) {
        e.setItsAddress(nextAddress);
    }
}
