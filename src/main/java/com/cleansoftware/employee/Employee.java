package com.cleansoftware.employee;

import com.cleansoftware.pay.Paycheck;
import com.cleansoftware.payment.affiliation.Affiliation;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.method.PaymentMethod;
import com.cleansoftware.payment.schedule.PaymentSchedule;

import java.util.Calendar;

public class Employee {
    private int empId;
    private String itsName;
    private String itsAddress;
    private PaymentSchedule paymentSchedule;
    private PaymentClassification paymentClassification;
    private PaymentMethod paymentMethod;
    private Affiliation affiliation;

    public Employee(int empId, String itsName, String itsAddress) {
        this.empId = empId;
        this.itsName = itsName;
        this.itsAddress = itsAddress;
    }


    public PaymentSchedule getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public PaymentClassification getPaymentClassification() {
        return paymentClassification;
    }

    public void setPaymentClassification(PaymentClassification paymentClassification) {
        this.paymentClassification = paymentClassification;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getItsName() {
        return itsName;
    }

    public void setItsName(String name) {
        this.itsName = name;
    }

    public String getItsAddress() {
        return this.itsAddress;
    }

    public void setItsAddress(String address) {
        this.itsAddress = address;
    }

    public Affiliation getAffiliation() {
        return this.affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public boolean isPayDate(Calendar payDate) {
        return paymentSchedule.isPayDate(payDate);
    }

    public void payday(Paycheck paycheck) {
        double grossPay = paymentClassification.calculatePay(paycheck);
        double deductions = affiliation.calculateDeductions(paycheck);
        double netPay = grossPay - deductions;
        paycheck.setGrossPay(grossPay);
        paycheck.setDeductions(deductions);
        paycheck.setNetPay(netPay);
        paymentMethod.pay(paycheck);
    }

    public Calendar getPayPeriodStartDate(Calendar payDate) {
        return paymentSchedule.getStartDate(payDate);
    }
}
