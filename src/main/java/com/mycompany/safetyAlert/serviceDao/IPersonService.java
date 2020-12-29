package com.mycompany.safetyAlert.serviceDao;

import com.mycompany.safetyAlert.model.Person;
import org.springframework.stereotype.Service;


public interface IPersonService {
    boolean createPerson (Person person);
    boolean deletePerson (Person person);
    boolean updatePerson (Person person);
}
