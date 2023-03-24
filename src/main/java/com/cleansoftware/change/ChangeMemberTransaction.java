package com.cleansoftware.change;

import com.cleansoftware.payment.affiliation.Affiliation;
import com.cleansoftware.payment.affiliation.UnionAffiliation;
import com.cleansoftware.transaction.ChangeAffiliationTransaction;

public class ChangeMemberTransaction extends ChangeAffiliationTransaction {
    private double chargeRate;

    public ChangeMemberTransaction(int empId, double chargeRate) {
        super(empId);
        this.chargeRate = chargeRate;
    }

    @Override
    public Affiliation getAffiliation() {
        return new UnionAffiliation(chargeRate);
    }
}
