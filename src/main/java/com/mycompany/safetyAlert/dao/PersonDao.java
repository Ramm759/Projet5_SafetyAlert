package com.mycompany.safetyAlert.dao;

import com.mycompany.safetyAlert.model.Person;

public interface PersonDao {
    boolean createPerson (Person person);
    boolean deletePerson (Person person);
    boolean updatePerson (Person person);
}
