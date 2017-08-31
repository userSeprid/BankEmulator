package com.bank.logic.controller.auxiliary;

import com.bank.logic.model.Person;
import com.bank.logic.service.HibernateService;

import java.util.StringTokenizer;


public class BalanceSectionImpl implements BalanceSection {


    private Person user;


    @Override
    public void init(HibernateService _service, Person currentUser, ScannerStub _scan) {
        HibernateService service = _service;
        this.user = currentUser;

        ScannerStub scan = _scan;
        boolean flag = false;
        while (!flag) {

            System.out.println("\nYou're in your account menu. \n" +
                    "Here you can work with your balance. \n" +
                    "From here you can enter to next categories (for navigation use a numeric keys)\n" +
                    "1 - Input money section. \n2 - Output money section. \n" +
                    "3 - Transfer operation. \n4 - Exit from this section. \n\n");

            user = service.getPersonById(user.getId());
            String answer = userAnswerTaker(scan, user);
            StringTokenizer tokenizer = new StringTokenizer(answer, " ");
            int subsection = Integer.parseInt(tokenizer.nextToken());
            int amount;
            switch (subsection) {
                case 1:
                    amount = Integer.parseInt(tokenizer.nextToken());
                    user = service.getPersonById(user.getId());
                    service.updatePersonBalance(user.getId(), user.getPersonBalance() + amount);
                    System.out.printf("You add %s to your balance.\n", amount);
                    break;
                case 2:
                    amount = Integer.parseInt(tokenizer.nextToken());
                    if (amount > 0) {
                        user = service.getPersonById(user.getId());
                        service.updatePersonBalance(currentUser.getId(), user.getPersonBalance() + amount);
                        System.out.printf("You take %s from your balance.\n", amount);
                    }
                    break;
                case 3:
                    amount = Integer.parseInt(tokenizer.nextToken());
                    if (amount > 0) {
                        Integer ID = Integer.parseInt(tokenizer.nextToken());
                        user = service.getPersonById(user.getId());
                        if (user.getPersonBalance() - amount >= 0 && service.getPersonById(ID) != null) {
                            service.transferOperation(currentUser.getId(), ID, amount);
                            System.out.printf("You transfer %s from your balance.\n", amount);
                        } else {
                            System.out.println("Operation canceled. Not enough money or incorrect ID.\n");
                        }
                    }
                    break;
                case 4:
                    flag = true;
                    System.out.println("Exit to main menu.\n\n");
                    break;
                case 0:
                    System.out.println("For navigation use a numeric keys\n" +
                            "1 - Input money section. \n2 - Output money section. \n" +
                            "3 - Transfer operation. \n4 - Exit from this section. \n");
                    break;
            }

        }
    }

    @Override
    public String userAnswerTaker(ScannerStub scan, Person user) {

        String answer = null;
        int choice = inputCheck(scan);
        Integer amount;

        switch (choice) {
            case 1:
                System.out.printf("Your current balance is %s . Please input amount of money that you want to input " +
                        "(If you change your mind input \"any word that not a number\") \n", user.getPersonBalance());

                amount = inputCheck(scan);
                answer = choice + " " + amount;
                break;
            case 2:
                System.out.printf("Your current balance is %s . Please input amount of money that you want get " +
                        "(If you change your mind input \"any word or character that not a number\")\n ", user.getPersonBalance());

                amount = inputCheck(scan);
                if (user.getPersonBalance() - amount >= 0) {
                    System.out.printf("You will take %s from your balance.\n", amount);
                    answer = choice + " " + amount;
                } else {
                    System.out.println("Sorry but don't have enough money.\n");
                    answer = choice + " 0";
                }
                break;
            case 3:
                System.out.printf("Your current balance is %s . Please input amount of money that you want transfer " +
                        "(If you change your mind input \"any word or character that not a number\" \n", user.getPersonBalance());

                amount = inputCheck(scan);

                if (amount > 0) {
                    System.out.println("Now input ID of person who will receive a money.\n");

                    Integer id = inputCheck(scan);
                    answer = choice + " " + amount + " " + id;

                }else answer = choice + " 0";

                break;
            case 4:
                answer = String.valueOf(choice);
                break;
            default:
                answer = "0";
                break;
        }


        return answer;
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


