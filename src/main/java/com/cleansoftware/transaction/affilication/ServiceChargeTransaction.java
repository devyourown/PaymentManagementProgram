package com.cleansoftware.transaction.affilication;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.affiliation.Affiliation;
import com.cleansoftware.payment.affiliation.UnionAffiliation;
import com.cleansoftware.transaction.Transaction;

public class ServiceChargeTransaction implements Transaction {
    private int memberId;
    private long date;
    private double charge;

    public ServiceChargeTransaction(int memberId, long date, double charge) {
        this.memberId = memberId;
        this.date = date;
        this.charge = charge;
    }
    @Override
    public void execute() {
        Employee e = PayrollDatabase.getInstance().getUnionMember(memberId);
        Affiliation af = e.getAffiliation();
        UnionAffiliation uaf = (UnionAffiliation) af;
        uaf.addServiceCharge(date, charge);
    }
}
