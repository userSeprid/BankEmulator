package com.bank.logic.controller.auxiliary;


import com.bank.logic.model.Person;
import com.bank.logic.service.HibernateService;

public interface AccountSection {

    void init(HibernateService service, Person currentUser, ScannerStub scan);

    String userAnswerTaker(ScannerStub scan, Person user);

}
