package com.bank.logic.controller.auxiliary;

import com.bank.logic.model.Person;
import com.bank.logic.service.HibernateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;


public class BalanceSectionImpl implements BalanceSection {

    private HibernateService service;
    private Person currentUser;
    private final Logger logger;
    private Scanner scan;
    BalanceSectionImpl balanceSection;

    public BalanceSectionImpl()
    {
        logger = LoggerFactory.getLogger(BalanceSectionImpl.class);
    }

    public void init(HibernateService service, Person currentUser) {

        balanceSection = new BalanceSectionImpl();

        logger.debug("BalanceSection start.");
        this.service = service;
        this.currentUser = currentUser;


        System.out.println("You're in your account menu. \n" +
                "Here you can work with your balance. \n" +
                "From here you can enter to next categories (for navigation use a numeric keys)\n" +
                "1 - Input money section. \n2 - Output money section. \n" +
                "3 - Transfer operation. \n4 - Exit from this section. \n");

        scan = new Scanner(System.in);
        boolean flag = false;
        while (!flag)
        {
            int choice = 0;

            System.out.println("Choose an operation.");

            try {
                choice = scan.nextInt();
            }catch (InputMismatchException ex) {
                logger.debug("User - {}, ID - {}, try to input {}", currentUser.getPersonName(), currentUser.getId(), String.valueOf(choice));
                System.out.println("Incorrect input");
            }
            Integer amount;
            switch (choice)
            {
                case 1:
                    logger.debug("User - {}, ID - {}, try to input money",currentUser.getPersonName(), currentUser.getId());
                    System.out.printf("Your current balance is %s . Please input amount of money that you want to input " +
                            "(If you change your mind input \"any word that not a number\" ", currentUser.getPersonBalance());

                    amount = balanceSection.inputCheck(scan);

                    if (amount != null) {
                        service.updatePersonBalance(currentUser.getId(), currentUser.getPersonBalance() + amount);
                        System.out.printf("You was add %s to your balance.", amount);
                        logger.debug("User - {}, ID - {} add - {} to his balance.", currentUser.getPersonName(), currentUser.getId(), amount);
                    }
                    break;
                case 2:
                    logger.debug("User - {}, ID - {}, try to get money",currentUser.getPersonName(), currentUser.getId());
                    System.out.printf("Your current balance is %s . Please input amount of money that you want get " +
                            "(If you change your mind input \"any word or character that not a number\" ", currentUser.getPersonBalance());

                    amount = balanceSection.inputCheck(scan);
                    if (amount != null) {
                        if (currentUser.getPersonBalance() - amount >= 0) {
                            service.updatePersonBalance(currentUser.getId(), currentUser.getPersonBalance() + amount);
                            System.out.printf("You was take %s from your balance.", amount);
                            logger.debug("User - {}, ID - {} get - {} from his balance.", currentUser.getPersonName(), currentUser.getId(), amount);
                        }else {
                            System.out.println("Sorry but don't have enough money.");
                            logger.debug("User - {}, ID - {}, try to get {} but transaction was canceled", currentUser.getPersonName(), currentUser.getId(), amount);
                        }
                    }
                    break;
                case 3:
                    logger.debug("User - {}, ID - {}, try to transfer money",currentUser.getPersonName(), currentUser.getId());
                    System.out.printf("Your current balance is %s . Please input amount of money that you want transfer " +
                            "(If you change your mind input \"any word or character that not a number\" ", currentUser.getPersonBalance());

                    amount = balanceSection.inputCheck(scan);

                    System.out.println("Now input ID of person who will receive a money.");

                    Integer ID = balanceSection.inputCheck(scan);



                    if (amount != null && ID != null) {
                        if (currentUser.getPersonBalance() - amount >= 0 && service.getPersonById(ID) != null) {
                            Person tempPerson = service.getPersonById(ID);
                            service.transferOperation(currentUser.getId(), ID, amount);
                            System.out.printf("You was transfer %s from your balance.", amount);
                            logger.debug("User - {}, ID - {} transfer - {} from his balance to user - {}, ID - {} balance.",
                                    currentUser.getPersonName(), currentUser.getId(), amount, tempPerson.getPersonName(), tempPerson.getId());
                        }else {
                            System.out.println("Sorry but don't have enough money or target ID doesn't available.");
                            logger.debug("User - {}, ID - {}, try to get {} but transaction was canceled", currentUser.getPersonName(), currentUser.getId(), amount);
                        }
                    }
                    break;
                case 4:
                    flag = true;
                    break;

            }
        }

    }

    private Integer inputCheck(Scanner scan)
    {
        Integer amount = null;
        try {
            amount = scan.nextInt();
        }catch (InputMismatchException ex){
            logger.debug("User - {}, ID - {}, cancel money operation", currentUser.getPersonName(), currentUser.getId());
            System.out.println("Return to balance section");
        }
        return amount;
    }

    public void auxiliaryFunctional() {

    }



}
