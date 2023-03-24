package com.cleansoftware.payment.affiliation;

import java.util.HashMap;
import java.util.Map;

public class UnionAffiliation implements Affiliation {
    private double chargeRate;
    private int memberId;
    private Map<Long, ServiceCharge> serviceCharges;

    public UnionAffiliation(int memberId, double chargeRate) {
        this.memberId = memberId;
        this.chargeRate = chargeRate;
        this.serviceCharges = new HashMap<>();
    }

    public void addServiceCharge(long date, double charge) {
        serviceCharges.put(date, new ServiceCharge(date, charge));
    }

    public ServiceCharge getServiceCharge(long date) {
        return serviceCharges.get(date);
    }

    public double getDues() {
        return chargeRate;
    }

    public int getMemberId() {
        return memberId;
    }
}
