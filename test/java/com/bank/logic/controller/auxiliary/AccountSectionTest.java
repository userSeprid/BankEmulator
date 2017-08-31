package com.bank.logic.controller.auxiliary;

import com.bank.logic.model.Person;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.when;



@RunWith(JUnitParamsRunner.class)
public class AccountSectionTest {

    private Person testPerson;

    public static final Object[] getEmailArguments()
    {
        return $(
                $(1, "no"),
                $(1,"bobby@mail.com")
        );
    }

    public static final Object[] getPasswordArguments()
    {
        return $(
                $(2, "no"),
                $(2, "newPassword")
        );
    }

    public static final Object[] getInfoAndExitArguments()
    {
        return $(
                $(3, "3"),
                $(4, "exit")
        );
    }



    @Before
    public void setUp() {
        //Here is hardcode functional of @RunWith(Mockito.class) because already use this annotation
        MockitoAnnotations.initMocks(this);
        testPerson = new Person("Bob", "ThisIsBob@mail.com", "BestBob", 0);
    }

    @InjectMocks
    AccountSectionImpl accountSection = new AccountSectionImpl();

    @Mock
    ScannerStub stub;

    //Checking email changing feature
    @Test
    @Parameters(method = "getEmailArguments")
    public void EmailChangingTest(int subsection, String answer) {
        when(stub.nextInt()).thenReturn(subsection);
        when(stub.next()).thenReturn(answer);

        Assert.assertEquals(answer, accountSection.userAnswerTaker(stub, testPerson));
    }


    //Checking password changing feature
    @Test
    @Parameters(method = "getPasswordArguments")
    public void PasswordChangingTest(int subsection, String answer) {
        when(stub.nextInt()).thenReturn(subsection);
        when(stub.next()).thenReturn(answer);

        Assert.assertEquals(answer, accountSection.userAnswerTaker(stub, testPerson));
    }

    //Checking info showing and exit features
    @Test
    @Parameters(method = "getInfoAndExitArguments")
    public void InfoAndExitTest(int subsection, String answer) {
        when(stub.nextInt()).thenReturn(subsection);

        Assert.assertEquals(answer, accountSection.userAnswerTaker(stub, testPerson));
    }
}