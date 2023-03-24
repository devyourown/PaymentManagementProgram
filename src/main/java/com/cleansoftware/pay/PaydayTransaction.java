package com.cleansoftware.pay;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.transaction.Transaction;

import java.util.*;

public class PaydayTransaction implements Transaction {
    private Calendar payDate;
    private Map<Integer, Paycheck> paychecks;

    public PaydayTransaction(Calendar date) {
        this.payDate = date;
        paychecks = new HashMap<>();
    }

    @Override
    public void execute() {
        List<Integer> empIds = PayrollDatabase.getInstance().getAllEmployeeIds();
        for (int id : empIds) {
            Employee e = PayrollDatabase.getInstance().getEmployee(id);
            if (e != null && e.isPayDate(payDate)) {
                Paycheck pc = new Paycheck(payDate);
                paychecks.put(id, pc);
                e.payday(pc);
            }
        }
    }

    public Paycheck getPaycheck(int empId) {
        return paychecks.get(empId);
    }
}
