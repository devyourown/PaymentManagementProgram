package com.cleansoftware.payment.affiliation;

import java.util.HashMap;
import java.util.Map;

public class UnionAffiliation implements Affiliation {
    private double chargeRate;
    private Map<Long, ServiceCharge> serviceCharges;

    public UnionAffiliation(double chargeRate) {
        this.chargeRate = chargeRate;
        this.serviceCharges = new HashMap<>();
    }

    public void addServiceCharge(long date, double charge) {
        serviceCharges.put(date, new ServiceCharge(date, charge));
    }

    public ServiceCharge getServiceCharge(long date) {
        return serviceCharges.get(date);
    }
}
