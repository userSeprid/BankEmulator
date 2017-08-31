package com.bank.logic;

import com.bank.logic.controller.MainLogic;
import com.bank.logic.model.Person;
import com.bank.logic.service.HibernateService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.InputMismatchException;
import java.util.Scanner;


public class AppMainLogic {

    public  void run() {

        Logger logger = LoggerFactory.getLogger(AppMainLogic.class);

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        }catch (Throwable ex){
            System.err.println("Failed to create SessionFactory object. " + ex);
            throw new ExceptionInInitializerError(ex);
        }

        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        MainLogic logic = (MainLogic) ctx.getBean("controller");
        HibernateService service = (HibernateService) ctx.getBean("service");
        logic.initLogic(service);

        System.out.println("Hello !\n" +
                "If new user input 1 for registration and " +
                "if you already have an account input 2 for authentication.\n" +
                "If you want to exit press 0.\n" +
                "Please use here only numeric keys\n");

        Scanner scan;
        boolean flag = false;
        while (!flag){
            System.out.println("Use numeric keys");

            scan = new Scanner(System.in);
            Integer userNumber = null;
            try {
                userNumber = scan.nextInt();
            }catch (InputMismatchException ex) {
                logger.debug("User  try to input incorrect value.");
                System.out.println("Incorrect input");
            }
            Person person;

            if (userNumber != null)
            {
                switch (userNumber) {

                    case 1:
                        logic.userCreation();

                        person = logic.authentication();
                        if (person != null) logic.userSession(person);
                        System.out.println("Bye!");
                        break;
                    case 2:
                        person = logic.authentication();
                        if (person != null) logic.userSession(person);
                        System.out.println("Bye!");
                        break;
                    case 0:
                        flag = true;
                        break;
                }
            }
        }

    }



}
