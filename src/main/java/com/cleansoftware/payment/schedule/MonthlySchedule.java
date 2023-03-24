package com.cleansoftware.payment.schedule;

import java.util.Calendar;

public class MonthlySchedule implements PaymentSchedule {

    @Override
    public boolean isPayDate(Calendar date) {
        return isLastDayOfMonth(date);
    }

    private boolean isLastDayOfMonth(Calendar date) {
        int m1 = date.get(Calendar.MONTH);
        int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);
        Calendar cloned = (Calendar) date.clone();
        cloned.set(Calendar.DAY_OF_MONTH, dayOfMonth + 1);
        int m2 = cloned.get(Calendar.MONTH);
        return (m1 != m2);
    }

    @Override
    public Calendar getStartDate(Calendar payDate) {
        int dayOfMonth = payDate.get(Calendar.DAY_OF_MONTH);
        Calendar result = (Calendar) payDate.clone();
        result.add(Calendar.DATE, -dayOfMonth + 1);
        return result;
    }
}
