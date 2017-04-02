package com.bank.logic.dao;

import com.bank.logic.model.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class DAOImpl implements DAO {
    private SessionFactory factory;
    private final Logger logger;

    public DAOImpl()
    {
        try {
            factory = new Configuration().configure().buildSessionFactory();
            logger = LoggerFactory.getLogger(DAOImpl.class);
        }catch (Throwable ex){
            System.out.println("Failed to create a session. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public Integer addPerson(String name, String email, String password)
    {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer personID = null;
        try {
            tx = session.beginTransaction();
            Person person = new Person(name, email, password);
            personID = (Integer) session.save(person);
            //logger.debug("New person was successfully added. Person details: ID - {}, Name - {}, Email - {},Password - {}",
            //        personID, name, email, password);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null)tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return personID;
    }

    public void deletePerson(Integer id)
    {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Person person = session.get(Person.class, id);
            session.delete(person);
            //logger.debug("Person was successfully deleted. Person details: ID - {}, Name - {}, Email - {},Password - {}, Balance - {}",
            //        id, person.getPersonName(), person.getPersonEmail(), person.getPersonPassword(), person.getPersonBalance());
            tx.commit();
        }catch (HibernateException e){
            if (tx!=null)tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    public List listPersonObjects()
    {
        List list = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            list = session.createQuery("FROM Person").list();
            logger.debug("Persons list: {}", list);
            tx.commit();
        }catch (HibernateException e){
            if (tx!=null)tx.rollback();
        }finally {
            session.close();
        }

        return list;
    }



    public Person getPersonById(Integer id)
    {
        Session session = factory.openSession();
        Transaction tx = null;
        Person person = null;
        try {
            tx = session.beginTransaction();
            person = session.get(Person.class, id);
            //logger.debug("Person was successfully received. Person details: ID - {}, Name - {}, Email - {},Password - {}, Balance - {}",
            //        id, person.getPersonName(), person.getPersonEmail(), person.getPersonPassword(), person.getPersonBalance());

            tx.commit();
        }catch (HibernateException e){
            if (tx!=null)tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return person;
    }

    public void updatePerson(Person person)
    {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(person);
            logger.debug("Person was successfully updated. New person details: ID - {}, Name - {}, Email - {},Password - {}, Balance - {}",
                    person.getId(), person.getPersonName(), person.getPersonEmail(), person.getPersonPassword(), person.getPersonBalance());
            tx.commit();
        }catch (HibernateException e){
            if (tx!=null)tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updatePersonEmail(Integer id, String newEmail)
    {

        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Person person = session.get(Person.class, id);
            person.setPersonEmail(newEmail);
            session.update(person);
            //logger.debug("Email was changed for person - {}, id - {}. New email - {}", person.getPersonName(), id, newEmail);
            tx.commit();
        }catch (HibernateException e){
            if (tx!=null)tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updatePersonPassword(Integer id, String newPassword)
    {

        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Person person = session.get(Person.class, id);
            person.setPersonPassword(newPassword);
            session.save(person);
            logger.debug("Password was changed for person - {}, id - {}. New password - {}", person.getPersonName(), id, newPassword);
            tx.commit();
        }catch (HibernateException e){
            if (tx!=null)tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updatePersonBalance(Integer id, Integer newBalance)
    {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Person person = session.get(Person.class, id);
            int previousBalance = person.getPersonBalance();
            person.setPersonBalance(newBalance);
            session.save(person);
            logger.debug("Balance was changed for person - {}, id - {}. Previous balance - {}, new balance - {}",
                   person.getPersonName(), id, previousBalance, newBalance);
            tx.commit();
        }catch (HibernateException e){
            if (tx!=null)tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void transferOperation(Integer senderId, Integer receiverId, Integer amount)
    {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Person sender = session.get(Person.class, senderId);
            if (sender.getPersonBalance()-amount >= 0)
            {
                sender.setPersonBalance(sender.getPersonBalance() - amount);
                Person receiver = session.get(Person.class, receiverId);
                receiver.setPersonBalance(receiver.getPersonBalance() + amount);
                logger.debug("Transfer was performed successfully. " +
                        "Person - {} (id-{}) at now have {}$ on his balance and person - {} (id-{}) have {}$ on his balance",
                        sender.getPersonName(), sender.getPersonBalance(), senderId,
                        receiver.getPersonName(), receiver.getPersonBalance(), receiverId);
                tx.commit();
            }else
            {
                logger.debug("Person has insufficient funds. Transfer operation can't be performed");
                System.out.println("Sorry, you can't did it.");
            }
        }catch (HibernateException e){
            if (tx!=null)tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }


}
