package com.bank.logic.controller.auxiliary;


import com.bank.logic.model.Person;
import com.bank.logic.service.HibernateService;

public interface BalanceSection {

    void init(HibernateService service, Person currentUser);

    //This method was created for case when extension requests more that one method.
    void auxiliaryFunctional();

}
