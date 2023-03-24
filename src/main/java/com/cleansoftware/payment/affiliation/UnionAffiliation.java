package com.cleansoftware.payment.affiliation;

import com.cleansoftware.pay.Paycheck;
import com.cleansoftware.payment.UtilDate;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UnionAffiliation implements Affiliation {
    private double chargeRate;
    private int memberId;
    private Map<Calendar, ServiceCharge> serviceCharges;

    public UnionAffiliation(int memberId, double chargeRate) {
        this.memberId = memberId;
        this.chargeRate = chargeRate;
        this.serviceCharges = new HashMap<>();
    }

    public void addServiceCharge(Calendar date, double charge) {
        serviceCharges.put(date, new ServiceCharge(date, charge));
    }

    public ServiceCharge getServiceCharge(Calendar date) {
        return serviceCharges.get(date);
    }

    public double getDues() {
        return chargeRate;
    }

    public int getMemberId() {
        return memberId;
    }

    @Override
    public double calculateDeductions(Paycheck paycheck) {
        int fridays = getNumberOfFridaysInPayPeriod(
                paycheck.getPayPeriodStartDate(),
                paycheck.getPayPeriodEndDate());
        double totalDues = getDues() * fridays;
        totalDues += getServiceCharges(paycheck);
        return totalDues;
    }

    private int getNumberOfFridaysInPayPeriod(Calendar payPeriodStart,
                                              Calendar payPeriodEnd) {
        int fridays = 0;
        Calendar day = (Calendar) payPeriodStart.clone();
        while (day.compareTo(payPeriodEnd) != 0) {
            if (day.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                fridays++;
            day.roll(Calendar.DATE, 1);
        }
        return fridays + 1;
    }

    private double getServiceCharges(Paycheck paycheck) {
        double result = 0;
        for (ServiceCharge sc : serviceCharges.values()) {
            if (UtilDate.isInPayPeriod(sc.getDate(), paycheck))
                result += sc.getAmount();
        }
        return result;
    }
}
