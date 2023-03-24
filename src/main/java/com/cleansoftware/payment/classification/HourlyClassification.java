package com.cleansoftware.payment.classification;

import com.cleansoftware.pay.Paycheck;
import com.cleansoftware.payment.timecard.TimeCard;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HourlyClassification implements PaymentClassification {
    private double hourlyRate;
    private Map<Calendar, TimeCard> timeCards;

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
        double result = 0;
        Calendar payDate = paycheck.getPayDate();
        for (TimeCard timecard : timeCards.values()) {
            if (isInPayPeriod(payDate, timecard.getDate()))
                result += calculatePayForTimeCard(timecard);
        }
        return result;
    }

    private boolean isInPayPeriod(Calendar payDate, Calendar cardDate) {
        int payWeek = DayUtil.getDaysAfterAD(payDate);
        int cardWeek = DayUtil.getDaysAfterAD(cardDate);
        return Math.abs(payWeek - cardWeek) <= 6;
    }

    private double calculatePayForTimeCard(TimeCard tc) {
        double hours = tc.getHours();
        double overtime = Math.max(0.0, hours - 8.0);
        double straightTime = hours - overtime;
        return straightTime * getHourlyRate() + overtime * getHourlyRate() * 1.5;
    }
}
