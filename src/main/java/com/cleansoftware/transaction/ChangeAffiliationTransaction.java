package com.cleansoftware.transaction;

import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.affiliation.Affiliation;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {

    public ChangeAffiliationTransaction(int empId) {
        super(empId);
    }

    public abstract Affiliation getAffiliation();

    @Override
    public void change(Employee e) {
        e.setAffiliation(getAffiliation());
    }
}
