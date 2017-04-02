package com.bank.logic.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "persons")
public class Person {

    @Id @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "name")
    private String personName;

    @Column(name = "email")
    private String personEmail;

    @Column(name = "password")
    private String personPassword;

    @Column(name = "balance")
    private Integer personBalance;

    @Column(name = "create_time")
    private Date createTime;

    public Person(){}


    public Person(String personName, String personEmail, String personPassword)
    {
        this.personName = personName;
        this.personEmail = personEmail;
        this.personPassword = personPassword;
        personBalance = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonPassword() {
        return personPassword;
    }

    public void setPersonPassword(String personPassword) {
        this.personPassword = personPassword;
    }

    public Integer getPersonBalance() {
        return personBalance;
    }

    public void setPersonBalance(Integer personBalance) {
        this.personBalance = personBalance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personName='" + personName + '\'' +
                ", personEmail='" + personEmail + '\'' +
                ", personPassword='" + personPassword + '\'' +
                ", personBalance=" + personBalance +
                ", createTime=" + createTime +
                '}';
    }
}
