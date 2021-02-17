package com.mycompany.safetyAlert.serviceDao;

import com.mycompany.safetyAlert.model.Person;
import com.mycompany.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceDao implements IPersonServiceDao {
    @Autowired
    DataRepository dataRepository;

    @Override
    public boolean createPerson(Person person) {
        dataRepository.getAllPersons().add(person); // ajout en mémoire de la nouvelle personne
        dataRepository.commit(); // écriture dans le fichier Json
        return true;
    }
    @Override

    public boolean deletePerson(Person person) {
        boolean result = dataRepository.getAllPersons().remove(person);
        dataRepository.commit();
        return result;
    }

    @Override
    public boolean updatePerson(Person person) {
        if (dataRepository.getAllPersons().remove(person)) {
            createPerson(person);
            return true;
        }
        return false;
    }
}
