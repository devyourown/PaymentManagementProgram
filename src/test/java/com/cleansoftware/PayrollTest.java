package com.cleansoftware;

import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.AddCommissionedEmployee;
import com.cleansoftware.employee.AddHourlyEmployee;
import com.cleansoftware.employee.AddSalariedEmployee;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.classification.CommissionedClassification;
import com.cleansoftware.payment.classification.HourlyClassification;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.classification.SalariedClassification;
import com.cleansoftware.payment.method.HoldMethod;
import com.cleansoftware.payment.method.PaymentMethod;
import com.cleansoftware.payment.receipt.SalesReceipt;
import com.cleansoftware.payment.schedule.BiweeklySchedule;
import com.cleansoftware.payment.schedule.MonthlySchedule;
import com.cleansoftware.payment.schedule.PaymentSchedule;
import com.cleansoftware.payment.schedule.WeeklySchedule;
import com.cleansoftware.payment.timecard.TimeCard;
import com.cleansoftware.transaction.DeleteEmployeeTransaction;
import com.cleansoftware.transaction.SalesReceiptTransaction;
import com.cleansoftware.transaction.TimeCardTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayrollTest {
    @Test
    void testAddSalariedEmployee() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();
        Employee e = payrollDatabase.getEmployee(empId);
        assertEquals("Bob", e.getItsName());

        PaymentClassification pc = e.getPaymentClassification();
        assertInstanceOf(SalariedClassification.class, pc);
        SalariedClassification sc = (SalariedClassification) pc;

        assertEquals(1000.0, sc.getSalary(), 0.001);
        PaymentSchedule ps = e.getPaymentSchedule();
        MonthlySchedule ms = (MonthlySchedule) ps;
        assertNotNull(ms);

        PaymentMethod pm = e.getPaymentMethod();
        HoldMethod hm = (HoldMethod) pm;
        assertNotNull(ms);
    }

    @Test
    void testAddHourlyEmployee() {
        int empId = 1;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bob", "Home", 1000.0);
        t.execute();

        PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();
        Employee e = payrollDatabase.getEmployee(empId);
        assertEquals("Bob", e.getItsName());

        PaymentClassification pc = e.getPaymentClassification();
        assertInstanceOf(HourlyClassification.class, pc);
        HourlyClassification sc = (HourlyClassification) pc;

        assertEquals(1000.0, sc.getHourlyRate(), 0.001);
        PaymentSchedule ps = e.getPaymentSchedule();
        WeeklySchedule ms = (WeeklySchedule) ps;
        assertNotNull(ms);

        PaymentMethod pm = e.getPaymentMethod();
        HoldMethod hm = (HoldMethod) pm;
        assertNotNull(ms);
    }

    @Test
    void testAddCommissionedEmployee() {
        int empId = 1;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home",
                1000.0, 10.0);
        t.execute();

        PayrollDatabase payrollDatabase = PayrollDatabase.getInstance();
        Employee e = payrollDatabase.getEmployee(empId);
        assertEquals("Bob", e.getItsName());

        PaymentClassification pc = e.getPaymentClassification();
        assertInstanceOf(CommissionedClassification.class, pc);
        CommissionedClassification cc = (CommissionedClassification) pc;

        assertEquals(1000.0, cc.getSalary(), 0.001);
        assertEquals(10.0, cc.getCommissionRate(), 0.001);
        PaymentSchedule ps = e.getPaymentSchedule();
        BiweeklySchedule ms = (BiweeklySchedule) ps;
        assertNotNull(ms);

        PaymentMethod pm = e.getPaymentMethod();
        HoldMethod hm = (HoldMethod) pm;
        assertNotNull(ms);
    }

    @Test
    void testDeleteEmployee() {
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home",
                2500, 3.2);
        t.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        DeleteEmployeeTransaction dt = new DeleteEmployeeTransaction(empId);
        dt.execute();
        e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNull(e);
    }

    @Test
    void testTimeCardTransaction() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        TimeCardTransaction tct = new TimeCardTransaction(2011031, 8.0, empId);
        tct.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        HourlyClassification hc = (HourlyClassification) pc;
        assertNotNull(hc);
        TimeCard tc = hc.getTimeCard(2011031);
        assertNotNull(tc);
        assertEquals(8.0, tc.getHours());
    }

    @Test
    void testSalesReceiptTransaction() {
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "hong", "Home",
                1000.0, 0.03);
        t.execute();
        SalesReceiptTransaction srt = new SalesReceiptTransaction(2011031, 110.0, empId);
        srt.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        CommissionedClassification cc = (CommissionedClassification) pc;
        assertNotNull(cc);
        SalesReceipt sr = cc.getSalesReceipt(2011031);
        assertNotNull(sr);
        assertEquals(110.0, sr.getAmount());
    }

    @Test
    void 
}