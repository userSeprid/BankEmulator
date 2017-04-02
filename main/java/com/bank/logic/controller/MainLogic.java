package com.bank.logic.controller;


import com.bank.logic.model.Person;
import com.bank.logic.service.HibernateService;

public interface MainLogic {

    void initLogic(HibernateService service);
    Person authentication();
    void userCreation();
    void userSession(Person currentUser);

}
