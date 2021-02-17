package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.model.Person;

public interface IPersonService {
    boolean createPerson (Person person);
    boolean deletePerson (Person person);
    boolean updatePerson (Person person);
}
