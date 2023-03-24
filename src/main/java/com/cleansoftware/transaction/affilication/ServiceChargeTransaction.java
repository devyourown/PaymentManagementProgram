package com.cleansoftware.transaction.affilication;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.affiliation.Affiliation;
import com.cleansoftware.payment.affiliation.UnionAffiliation;
import com.cleansoftware.transaction.Transaction;

import java.util.Calendar;

public class ServiceChargeTransaction implements Transaction {
    private int memberId;
    private Calendar date;
    private double charge;

    public ServiceChargeTransaction(int memberId, Calendar date, double charge) {
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
