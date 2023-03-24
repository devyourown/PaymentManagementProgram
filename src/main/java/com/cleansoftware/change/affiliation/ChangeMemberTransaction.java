package com.cleansoftware.change.affiliation;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.affiliation.Affiliation;
import com.cleansoftware.payment.affiliation.UnionAffiliation;
import com.cleansoftware.transaction.change.ChangeAffiliationTransaction;

public class ChangeMemberTransaction extends ChangeAffiliationTransaction {
    private double chargeRate;
    private int memberId;

    public ChangeMemberTransaction(int empId, int memberId, double chargeRate) {
        super(empId);
        this.memberId = memberId;
        this.chargeRate = chargeRate;
    }

    @Override
    public Affiliation getAffiliation() {
        return new UnionAffiliation(memberId, chargeRate);
    }

    @Override
    public void recordMembership(Employee e) {
        PayrollDatabase.getInstance().addUnionMember(memberId, e);
    }
}
