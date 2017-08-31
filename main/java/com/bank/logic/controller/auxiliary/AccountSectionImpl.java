package com.bank.logic.controller.auxiliary;

import com.bank.logic.model.Person;
import com.bank.logic.service.HibernateService;


public class AccountSectionImpl implements AccountSection {
    private HibernateService service;
    private Person currentUser;
    private ScannerStub scannerStub;
    private boolean flag = false;
    private int userSubsectionChoose;

    public AccountSectionImpl() {
        userSubsectionChoose = 0;
    }

    @Override
    public void init(HibernateService service, Person _currentUser, ScannerStub scan) {

        this.service = service;
        this.currentUser = _currentUser;

        System.out.println("You're in your account menu. \n" +
                "Here you can update account information. \n" +
                "From here you can enter to next categories (for navigation use a numeric keys):\");\n" +
                "1 - Change your email. \n2 - Change your password. \n" +
                "3 - Show account info \n4 - Exit from this section. \n");

        this.scannerStub = scan;
        flag = false;

        while (!flag) {

            currentUser = service.getPersonById(currentUser.getId());
            String userAnswer = userAnswerTaker(scannerStub, currentUser);
            if (!userAnswer.isEmpty()) {

                if ((userSubsectionChoose == 1 || userSubsectionChoose == 2 || userSubsectionChoose == 3) && !userAnswer.equals("no")) {
                    switch (userSubsectionChoose) {
                        case 1:
                            service.updatePersonEmail(currentUser.getId(), userAnswer);
                        case 2:
                            service.updatePersonPassword(currentUser.getId(), userAnswer);
                        case 3:
                            System.out.printf("ID - %s \nName - %s \nEmail - %s \nPassword - %s \n",
                                    currentUser.getId(), currentUser.getPersonName(), currentUser.getPersonEmail(), currentUser.getPersonPassword());
                    }
                } else if (userAnswer.equals("exit")) flag = true;
            }
        }
        userSubsectionChoose = 0;
    }


    //These method use my stub for java.util.Scanner for reading input from  console.

    /**
     * Take a several inputs from console that connected with account data changing section
     *
     * @param scan it is stub of java.util.Scanner for reading input from console
     * @param user it's a instance of Person object that contain info about current user
     * @return a String that contain user chosen operation
     */
    @Override
    public String userAnswerTaker(ScannerStub scan, Person user) {
        //Choosing one of 4 possible subsection
        int subsection = inputCheck(scan);
        String userAnswer = null;
        switch (subsection) {
            //Email changing subsection
            case 1:
                userSubsectionChoose = 1;
                System.out.printf("Your current email is %s . Please input your new email (If you change your mind input " +
                        "\"no\" with the same case and without additional spacing.)\n", user.getPersonEmail());

                //If user input "no" email won't change else this input will be new email
                userAnswer = scannerStub.next();
                if (userAnswer.equals("no")) {
                    System.out.println("exit to account section.\n");
                    return "no";
                }
                return userAnswer;
            //Password changing section
            case 2:
                userSubsectionChoose = 2;
                System.out.printf("Your current password is %s . Please input your new password (If you change your mind input " +
                        "\"no\" with the same case and without additional spacing.)\n", user.getPersonPassword());

                //If user input "no" password won't change else this input will be new password
                userAnswer = scannerStub.next();
                if (userAnswer.equals("no")) {
                    System.out.println("exit to account section.\n");
                    return "no";
                }
                return userAnswer;

            //Account information subsection. This section show all account info on console
            case 3:
                userSubsectionChoose = 3;
                return "3";

            //Exit subsection. If user choose this subsection, he will be returned to program main section
            case 4:
                userSubsectionChoose = 4;
                System.out.println("exit to account section.\n");
                return "exit";
        }
        //If input was incorrect method return "null" and user receive a "error" message that show him right input
        return null;
    }

    private int inputCheck(ScannerStub scan) {
        Integer amount = 0;
        try {
            amount = Integer.parseInt(scan.next());
        } catch (NumberFormatException ex) {
            System.out.println("Incorrect input\n");
        }
        if (amount < 0) amount = 0;
        return amount;
    }

}