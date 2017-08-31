package com.bank;


import com.bank.logic.AppMainLogic;
import com.bank.logic.controller.MainLogic;
import com.bank.logic.controller.auxiliary.AccountSectionImpl;
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

public class App {


    public static void main(String[] args) {

        new AppMainLogic().run();




    }



}
