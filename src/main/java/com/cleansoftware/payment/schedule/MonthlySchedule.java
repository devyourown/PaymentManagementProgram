package com.cleansoftware.payment.schedule;

import java.util.Calendar;

public class MonthlySchedule implements PaymentSchedule {
    private boolean isLastDayOfMonth(Calendar date) {
        int m1 = date.get(Calendar.MONTH);
        int day_of_month = date.get(Calendar.DAY_OF_MONTH);
        date.set(Calendar.DAY_OF_MONTH, day_of_month + 1);
        int m2 = date.get(Calendar.MONTH);
        return (m1 != m2);
    }

    @Override
    public boolean isPayDate(Calendar date) {
        return isLastDayOfMonth(date);
    }
}
