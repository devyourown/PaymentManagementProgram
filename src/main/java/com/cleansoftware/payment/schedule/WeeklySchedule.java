package com.cleansoftware.payment.schedule;

import com.cleansoftware.payment.schedule.PaymentSchedule;

import java.util.Calendar;

public class WeeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(Calendar date) {
        return date.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }

    @Override
    public Calendar getStartDate(Calendar payDate) {
        Calendar result = (Calendar) payDate.clone();
        result.roll(Calendar.DATE, -7);
        return result;
    }
}
