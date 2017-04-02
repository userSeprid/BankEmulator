package com.bank.logic.service;

import com.bank.logic.dao.DAO;
import com.bank.logic.model.Person;
import java.util.List;


public class HibernateService {

    private DAO thisDao;

    public HibernateService(DAO DAOImpl)
    {
        this.thisDao = DAOImpl;
    }

    public Integer addPerson(String name, String email, String password) {return thisDao.addPerson(name, email, password);}

    public void deletePerson(Integer id)
    {
        thisDao.deletePerson(id);
    }

    public List listPersonObjects(){return thisDao.listPersonObjects();}

    public Person getPersonById(Integer id)
    {
        return thisDao.getPersonById(id);
    }

    public void updatePerson(Person person)
    {
        thisDao.updatePerson(person);
    }

    public void updatePersonEmail(Integer id, String newEmail)
    {
        thisDao.updatePersonEmail(id, newEmail);
    }

    public void updatePersonPassword(Integer id, String newPassword)
    {
        thisDao.updatePersonPassword(id, newPassword);
    }

    public void updatePersonBalance(Integer id, Integer newBalance)
    {
        thisDao.updatePersonBalance(id, newBalance);
    }

    public void transferOperation(Integer senderId, Integer receiverId, Integer amount) {thisDao.transferOperation(senderId, receiverId, amount);}
}
