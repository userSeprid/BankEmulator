package com.bank.logic.dao;


import com.bank.logic.model.Person;

import java.util.List;

public interface DAO {

    public Integer addPerson(String name, String email, String password);
    public void deletePerson(Integer id);
    public List listPersonObjects();
    public Person getPersonById(Integer id);
    public void updatePerson(Person person);
    public void updatePersonEmail(Integer id, String newEmail);
    public void updatePersonPassword(Integer id, String newPassword);
    public void updatePersonBalance(Integer id, Integer newBalance);
    public void transferOperation(Integer senderId, Integer receiverId, Integer amount);


}
