package com.cleansoftware.transaction.change;

import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.affiliation.Affiliation;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {

    public ChangeAffiliationTransaction(int empId) {
        super(empId);
    }

    public abstract Affiliation getAffiliation();
    public abstract void recordMembership(Employee e);

    @Override
    public void change(Employee e) {
        recordMembership(e);
        e.setAffiliation(getAffiliation());
    }
}
