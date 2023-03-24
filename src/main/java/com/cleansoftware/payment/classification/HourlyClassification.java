package com.cleansoftware.payment.classification;

import com.cleansoftware.pay.Paycheck;
import com.cleansoftware.payment.timecard.TimeCard;

import java.util.HashMap;
import java.util.Map;

public class HourlyClassification implements PaymentClassification {
    private double hourlyRate;
    private Map<Integer, TimeCard> timeCards;

    public HourlyClassification(double hourlyRate) {
        this.hourlyRate = hourlyRate;
        timeCards = new HashMap<>();
    }

    public double getHourlyRate() {
        return this.hourlyRate;
    }

    public TimeCard getTimeCard(int date) {
        return timeCards.get(date);
    }

    public void addTimeCard(TimeCard timeCard) {
        this.timeCards.put(timeCard.getDate(), timeCard);
    }

    @Override
    public double calculatePay(Paycheck paycheck) {
        return 0;
    }
}
