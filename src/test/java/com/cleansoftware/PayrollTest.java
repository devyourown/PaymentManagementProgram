package com.cleansoftware;

import com.cleansoftware.change.*;
import com.cleansoftware.database.PayrollDatabase;
import com.cleansoftware.employee.AddCommissionedEmployee;
import com.cleansoftware.employee.AddHourlyEmployee;
import com.cleansoftware.employee.AddSalariedEmployee;
import com.cleansoftware.employee.Employee;
import com.cleansoftware.payment.affiliation.NoAffiliation;
import com.cleansoftware.payment.affiliation.ServiceCharge;
import com.cleansoftware.payment.affiliation.UnionAffiliation;
import com.cleansoftware.payment.classification.CommissionedClassification;
import com.cleansoftware.payment.classification.HourlyClassification;
import com.cleansoftware.payment.classification.PaymentClassification;
import com.cleansoftware.payment.classification.SalariedClassification;
import com.cleansoftware.payment.method.DirectMethod;
import com.cleansoftware.payment.method.HoldMethod;
import com.cleansoftware.payment.method.MailMethod;
import com.cleansoftware.payment.method.PaymentMethod;
import com.cleansoftware.payment.receipt.SalesReceipt;
import com.cleansoftware.payment.schedule.BiweeklySchedule;
import com.cleansoftware.payment.schedule.MonthlySchedule;
import com.cleansoftware.payment.schedule.PaymentSchedule;
import com.cleansoftware.payment.schedule.WeeklySchedule;
import com.cleansoftware.payment.timecard.TimeCard;
import com.cleansoftware.transaction.DeleteEmployeeTransaction;
import com.cleansoftware.transaction.SalesReceiptTransaction;
import com.cleansoftware.transaction.ServiceChargeTransaction;
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
    void testAddServiceCharge() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        UnionAffiliation af = new UnionAffiliation(12.5);
        e.setAffiliation(af);
        int memberId = 86;
        PayrollDatabase.getInstance().addUnionMember(memberId, e);
        ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, 20011101, 12.95);
        sct.execute();
        ServiceCharge sc = af.getServiceCharge(20011101);
        assertNotNull(sc);
        assertEquals(12.95, sc.getAmount(), 0.01);
    }

    @Test
    void testChangeNameTransaction() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "Bill", "Home",
                15.25);
        t.execute();
        ChangeNameTransaction cnt = new ChangeNameTransaction(empId, "Bob");
        cnt.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        assertEquals("Bob", e.getItsName());
    }

    @Test
    void testChangeAddressTransaction() {
        int empId = 2;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "remote",
                1000.0);
        t.execute();
        ChangeAddressTransaction cat = new ChangeAddressTransaction(empId, "Home");
        cat.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        assertEquals("Home", e.getItsAddress());
    }

    @Test
    void testChangeHourlyTransaction() {
        int empId = 3;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Lance", "Home",
                2500, 3.2);
        t.execute();
        ChangeHourlyTransaction cht = new ChangeHourlyTransaction(empId, 27.53);
        cht.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        assertNotNull(pc);
        HourlyClassification hc = (HourlyClassification) pc;
        assertNotNull(hc);
        assertEquals(27.53, hc.getHourlyRate(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        WeeklySchedule ws = (WeeklySchedule) ps;
        assertNotNull(ws);
    }

    @Test
    void testChangeSalariedTransaction() {
        int empId = 4;
        AddCommissionedEmployee t = new AddCommissionedEmployee(empId, "Bob", "Home",
                100000.0, .32);
        t.execute();
        ChangeSalariedTransaction cst = new ChangeSalariedTransaction(empId, 1000.0);
        cst.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        assertNotNull(pc);
        SalariedClassification sc = (SalariedClassification) pc;
        assertNotNull(sc);
        assertEquals(1000, sc.getSalary(), 0.001);
        PaymentSchedule ps = e.getPaymentSchedule();
        MonthlySchedule ms = (MonthlySchedule) ps;
        assertNotNull(ms);
    }

    @Test
    void testChangeCommissionedTransaction() {
        int empId = 1;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "eunji", "Home",
                90000);
        t.execute();
        ChangeCommissionedTransaction cct = new ChangeCommissionedTransaction(empId, 10000.0, 9.0);
        cct.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        assertNotNull(pc);
        CommissionedClassification cc = (CommissionedClassification) pc;
        assertNotNull(cc);
        assertEquals(10000.0, cc.getSalary(), 0.001);
        assertEquals(9.0, cc.getCommissionRate(), 0.001);
    }

    @Test
    void testChangeDirectTransaction() {
        int empId = 3;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "Bob", "Home",
                1000.0);
        t.execute();
        ChangeDirectTransaction cdt = new ChangeDirectTransaction(empId);
        cdt.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentMethod pm = e.getPaymentMethod();
        DirectMethod dm = (DirectMethod) pm;
        assertNotNull(dm);
    }

    @Test
    void testChangeMailTransaction() {
        int empId = 10;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "eunji", "Home",
                1000.0);
        t.execute();
        ChangeMailTransaction cmt = new ChangeMailTransaction(empId);
        cmt.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        PaymentMethod pm = e.getPaymentMethod();
        MailMethod dm = (MailMethod) pm;
        assertNotNull(dm);
    }

    @Test
    void testChangeHoldTransaction() {
        int empId = 2;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "eunji", "Home",
                1000.0);
        t.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        assertNotNull(e);
        ChangeMailTransaction cmt = new ChangeMailTransaction(empId);
        cmt.execute();
        assertInstanceOf(MailMethod.class, e.getPaymentMethod());
        ChangeHoldTransaction cht = new ChangeHoldTransaction(empId);
        cht.execute();
        assertInstanceOf(HoldMethod.class, e.getPaymentMethod());
    }

    @Test
    void testChangeMemberTransaction() {
        int empId = 2;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "eunji", "Home",
                1000.0);
        t.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, 10.2);
        cmt.execute();
        assertInstanceOf(UnionAffiliation.class, e.getAffiliation());
    }

    @Test
    void testChangeUnaffiliatedTransaction() {
        int empId = 2;
        AddSalariedEmployee t = new AddSalariedEmployee(empId, "eunji", "Home",
                1000.0);
        t.execute();
        Employee e = PayrollDatabase.getInstance().getEmployee(empId);
        ChangeUnaffiliatedTransaction cut = new ChangeUnaffiliatedTransaction(empId);
        cut.execute();
        assertInstanceOf(NoAffiliation.class, e.getAffiliation());
    }
}