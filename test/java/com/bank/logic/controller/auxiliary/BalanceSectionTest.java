package com.bank.logic.controller.auxiliary;


import com.bank.logic.model.Person;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class BalanceSectionTest {

    private Person testPerson;


    public static final Object[] getMoneyInputArguments() {
        return $(
                $("1", "100"),
                $("1", "20"),
                $("1", "2"),
                $("1", "4000")
        );
    }

    public static final Object[] getMoneyOutputArguments() {
        return $(
                $("2", "100"),
                $("2", "200"),
                $("2", "2000"),
                $("2", "350")
        );
    }

    public static final Object[] getMoneyTransferArguments() {
        return $(
                $("3", "100", "5"),
                $("3", "50", "10"),
                $("3", "1", "2"),
                $("3", "25", "15")

        );
    }

    public static final Object[] getInfoAndExitSectionArguments() {
        return $(
                $("4"),
                $("11"),
                $("8"),
                $("0")
        );

    }

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        testPerson = new Person("Bob", "ThisIsBob@mail.com", "BestBob", 0);
        testPerson.setPersonBalance(300);
    }

    @InjectMocks
    BalanceSection balanceSection = new BalanceSectionImpl();

    @Mock
    ScannerStub stub;


    @Test
    @Parameters(method = "getMoneyInputArguments")
    public void moneyInputTest(String subsection, String answer)
    {
        when(stub.next()).thenReturn(subsection).thenReturn(answer);
        String result = subsection + " " + answer;

        Assert.assertEquals(result, balanceSection.userAnswerTaker(stub, testPerson));
    }


    @Test
    @Parameters(method = "getMoneyOutputArguments")
    public void moneyOutputTest(String subsection, String answer)
    {
        when(stub.next()).thenReturn(subsection).thenReturn(answer);
        String result;

        if (Integer.parseInt(answer) > testPerson.getPersonBalance()) {
            result = subsection + " 0";
        }else {
            result = subsection + " " + answer;
        }

        Assert.assertEquals(result, balanceSection.userAnswerTaker(stub, testPerson));
    }


    @Test
    @Parameters(method = "getMoneyTransferArguments")
    public void moneyTransferTest(String subsection, String answer, String id)
    {
        when(stub.next()).thenReturn(subsection).thenReturn(answer).thenReturn(id);
        String result = subsection + " " + answer + " " + id;

        Assert.assertEquals(result, balanceSection.userAnswerTaker(stub, testPerson));
    }


    @Test
    @Parameters(method = "getInfoAndExitSectionArguments")
    public void infoAndExitSectionTest(String subsection)
    {
        when(stub.next()).thenReturn(subsection);
        String result;
        if (subsection.equals("4")){
            result = subsection;
        } else result = "0";


        Assert.assertEquals(result, balanceSection.userAnswerTaker(stub, testPerson));
    }






}
