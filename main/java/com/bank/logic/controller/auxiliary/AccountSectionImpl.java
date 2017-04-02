package com.bank.logic.controller.auxiliary;

import com.bank.logic.model.Person;
import com.bank.logic.service.HibernateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;


public class AccountSectionImpl implements AccountSection {
    private HibernateService service;
    private Person currentUser;
    private final Logger logger;
    private Scanner scan;

    public AccountSectionImpl() {
        logger = LoggerFactory.getLogger(AccountSectionImpl.class);
    }


    public void init(HibernateService service, Person currentUser) {

        logger.debug("AccountSection start.");
        this.service = service;
        this.currentUser = currentUser;

        System.out.println("You're in your account menu. \n" +
                "Here you can update account information. \n" +
                "From here you can enter to next categories (for navigation use a numeric keys):\");\n" +
                "1 - Change your email. \n2 - Change your password. \n" +
                "3 - Show account info \n4 - Exit from this section. \n");

        scan = new Scanner(System.in);
        boolean flag = false;


        while (!flag)
        {
            Integer choice = null;

            System.out.println("(Account Section) Choose an operation.");

            try {
                choice = scan.nextInt();
            }catch (InputMismatchException ex) {
                logger.debug("User - {}, ID - {}, try to input ", currentUser.getPersonName(), currentUser.getId(), choice);
                System.out.println("Incorrect input");
            }

            switch (choice)
            {
                case 1:
                    logger.debug("User - {}, ID - {}, try to change email",currentUser.getPersonName(), currentUser.getId());
                    System.out.printf("Your current email is %s . Please input your new email (If you change your mind input \"no\" " +
                                    "with the same case and without additional spacing.)", currentUser.getPersonEmail());

                    String newEmail = scan.next();
                    if (newEmail.equals("no")) {
                        System.out.println("exit to account section.");
                        logger.debug("User - {}, ID - {} cancel email changing.", currentUser.getPersonName(), currentUser.getId());
                    }
                    else {
                        service.updatePersonEmail(currentUser.getId(), newEmail);
                        logger.debug("User - {}, ID - {} was successfully changed email. New email is - {}", currentUser.getPersonName(),
                                currentUser.getId(), newEmail);
                        System.out.printf("Your email changed. Your new email is %s \n", newEmail);
                    }
                    break;
                case 2:
                    logger.debug("User - {}, ID - {}, try to change password",currentUser.getPersonName(), currentUser.getId());
                    System.out.printf("Your current password is %s . Please input your new password (If you change your mind input \"no\" " +
                            "with the same case and without additional spacing.)", currentUser.getPersonPassword());

                    String newPassword = scan.next();
                    if (newPassword.equals("no")) {
                        System.out.println("exit to account section.");
                        logger.debug("User - {}, ID - {} cancel password changing.", currentUser.getPersonName(), currentUser.getId());
                    }
                    else {
                        service.updatePersonPassword(currentUser.getId(), newPassword);
                        logger.debug("User - {}, ID - {} was successfully changed password. New password is - {}", currentUser.getPersonName(),
                               currentUser.getId(), newPassword);
                        System.out.printf("Your password changed. Your new password is %s", newPassword);
                    }
                    break;
                case 3:
                    logger.debug("User - {}, ID - {}, check his account info",currentUser.getPersonName(), currentUser.getId());
                    System.out.printf("ID - %s \nName - %s \nEmail - %s \nPassword - %s \n",
                            currentUser.getId(), currentUser.getPersonName(), currentUser.getPersonEmail(), currentUser.getPersonPassword());
                    break;
                case 4:
                    logger.debug("User - {}, ID - {}, exit from account section.",currentUser.getPersonName(), currentUser.getId());
                    flag = true;
                    break;

            }

        }



    }



    public void auxiliaryFunctional() {}
}
