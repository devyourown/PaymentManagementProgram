package com.cleansoftware.change;

import com.cleansoftware.payment.affiliation.Affiliation;
import com.cleansoftware.payment.affiliation.NoAffiliation;
import com.cleansoftware.transaction.ChangeAffiliationTransaction;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {

    public ChangeUnaffiliatedTransaction(int empId) {
        super(empId);
    }

    @Override
    public Affiliation getAffiliation() {
        return new NoAffiliation();
    }
}
