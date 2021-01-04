package com.mycompany.safetyAlert.dao;

import com.mycompany.safetyAlert.exceptions.DataAlreadyExistException;
import com.mycompany.safetyAlert.exceptions.DataNotFoundException;
import com.mycompany.safetyAlert.model.Person;
import com.mycompany.safetyAlert.repository.DataRepository;
import com.mycompany.safetyAlert.serviceDao.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonDaoImpl implements PersonDao {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    IPersonService personService;

    @Override
    public boolean createPerson(Person person) {
        // On vérifie que la personne n'existe pas dans la Dao ( nom + prénom)
        if (!dataRepository.database.getPersons().contains(person)) {
            personService.createPerson(person);
            return true;
        } else {
            throw new DataAlreadyExistException("La personne " + person.getFirstName() + " " + person.getLastName() + " existe déjà.");
        }
    }

    @Override
    public boolean deletePerson(Person person) {
        if (!personService.deletePerson(person)){
            throw new DataNotFoundException("La personne " + person.getFirstName() + " " + person.getLastName() + " n'existe pas.");
        }
        return true;
    }

    @Override
    public boolean updatePerson(Person person) {
        if (!personService.updatePerson(person)) {
            throw new DataNotFoundException("La personne " + person.getFirstName() + " " + person.getLastName() + " n'existe pas.");
        }
        return true;
    }
}
