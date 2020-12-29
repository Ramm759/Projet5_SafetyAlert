package com.mycompany.safetyAlert.dto;

import java.util.ArrayList;
import java.util.List;

public class PersonInfoWithMinor {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private int age;
    private int nbMineurs;
    private int nbMajeurs;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNbMineurs() {
        return nbMineurs;
    }

    public void setNbMineurs(int nbMineurs) {
        this.nbMineurs = nbMineurs;
    }

    public int getNbMajeurs() {
        return nbMajeurs;
    }

    public void setNbMajeurs(int nbMajeurs) {
        this.nbMajeurs = nbMajeurs;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
