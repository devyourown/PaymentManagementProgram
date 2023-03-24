package com.cleansoftware.payment.schedule;

import java.util.Calendar;

public class BiweeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDate(Calendar date) {
        return date.get(Calendar.WEEK_OF_MONTH) % 2 == 0 &&
                date.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }

    @Override
    public Calendar getStartDate(Calendar payDate) {
        Calendar result = (Calendar) payDate.clone();
        result.roll(Calendar.DATE, -14);
        return result;
    }
}
