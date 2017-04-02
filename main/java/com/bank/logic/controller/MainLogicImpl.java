package com.bank.logic.controller;

import com.bank.logic.controller.auxiliary.*;
import com.bank.logic.model.Person;
import com.bank.logic.service.HibernateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;


public class MainLogicImpl implements MainLogic{
    private HibernateService service;
    private final Logger logger;
    private Scanner scan;
    private AccountSection accountSection;
    private BalanceSection balanceSection;
    private LanguageSection languageSection;

    public MainLogicImpl(AccountSection accountSection, BalanceSection balanceSection, LanguageSection languageSection)
    {
        logger = LoggerFactory.getLogger(MainLogicImpl.class);
        this.accountSection = accountSection;
        this.balanceSection = balanceSection;
        this.languageSection = languageSection;
    }

    public void initLogic(HibernateService service){
        this.service = service;
        logger.debug("Logic initialized.");
        scan = new Scanner(System.in);
    }

    public Person authentication(){
        boolean answer = false;
        // Get form user his ID

        logger.debug("Authentication start.");
        System.out.println("You're in authentication menu.\n" +
                "Please input your ID");
        Integer userID = null;
        Person person = null;
        try {
            userID = scan.nextInt();

            logger.debug("User ID {}", userID);


            person = service.getPersonById(userID);

            // Get and check password
            System.out.println("Input your password");
            String userPassword = scan.next();


            // Return true if passwords match
            if (person.getPersonPassword().equals(userPassword))answer = true;

        }catch (InputMismatchException ex){
            logger.debug("User input incorrect id - {}", userID);
            System.out.println("Incorrect ID");
            ex.printStackTrace();
        }

        if (answer && person != null)return person;
        else return null;
    }

    public void userCreation(){

        // User creation start
        System.out.println("Hello. You in new user creation menu. Please input correct information about you");

        //Take a user data
        System.out.println("Write your Name \n");
        String username = scan.nextLine();
        logger.debug("New user input - {} as his name",username);
        System.out.println("Write your email \n");
        String userEmail = scan.next();
        logger.debug("New user input - {} as his email", userEmail);
        System.out.println("Write your password \n");
        String userPassword = scan.next();
        logger.debug("New user input - {} as his password", userPassword);

        //Insert information into database
        Integer tempID = service.addPerson(username, userEmail, userPassword);
        logger.debug("New user - {} was created", service.getPersonById(tempID));

        //Show to user his data
        System.out.println(service.getPersonById(tempID));
    }


    public void userSession(Person currentUser)
    {
        logger.debug("User {}, ID - {} enter in his account", currentUser.getPersonName(), currentUser.getId());

        //Show a greeting message to user
        greeting(currentUser);
        boolean exit = false;

        //This cycle is responsible for interacting with user
        // User control by numeric key
        while (!exit)
        {
            System.out.println("Write  - 0 if you want to see control keys");
            Integer choice = null;
            try {
                choice = scan.nextInt();
            }catch (InputMismatchException ex){
                logger.debug("User - {}, ID - {}, try to input {}", currentUser.getPersonName(), currentUser.getId(), choice);
                System.out.println("Incorrect input");
            }
            switch (choice){
                case 1:
                    //Enter in a language section
                    logger.debug("User - {}, ID - {}, enter in a language section",currentUser.getPersonName(), currentUser.getId());
                    languageSection.init();
                    break;
                case 2:
                    //Enter in an account section
                    logger.debug("User - {}, ID - {}, enter in an account section",currentUser.getPersonName(), currentUser.getId());
                    accountSection.init(service, currentUser);
                    break;
                case 3:
                    //Enter in a balance section
                    logger.debug("User - {}, ID - {}, enter in a balance section",currentUser.getPersonName(), currentUser.getId());
                    balanceSection.init(service, currentUser);
                    break;
                case 4:
                    //Exit from user account
                    logger.debug("User - {}, ID - {}, exit from his account", currentUser.getPersonName(), currentUser.getId());
                    exit = true;
                    break;
                case 0:
                    //Show a control keys
                    logger.debug("Show control keys to user - {}, ID - {}", currentUser.getPersonName(), currentUser.getId());
                    greeting(currentUser);
                    break;
            }
        }

    }




    private void greeting(Person currentUser)
    {
        System.out.println("Hello " + currentUser.getPersonName());
        System.out.println("From here you can enter to next categories (for navigation use a numeric keys):");
        System.out.println("1 - Change language \n2 - Work with your account data \n" +
                "3 - Work with your balance\n4 - Exit from account \n");
    }
}
