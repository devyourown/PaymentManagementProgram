package com.cleansoftware.change.affiliation;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.affiliation.Affiliation;
import com.cleansoftware.payment.affiliation.NoAffiliation;
import com.cleansoftware.payment.affiliation.UnionAffiliation;
import com.cleansoftware.transaction.change.ChangeAffiliationTransaction;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {

    public ChangeUnaffiliatedTransaction(int empId) {
        super(empId);
    }

    @Override
    public Affiliation getAffiliation() {
        return new NoAffiliation();
    }

    @Override
    public void recordMembership(Employee e) {
        if (e.getAffiliation() instanceof UnionAffiliation) {
            UnionAffiliation ua = (UnionAffiliation) e.getAffiliation();
            PayrollDatabase.getInstance().removeUnionMember(ua.getMemberId());
        }
    }
}
