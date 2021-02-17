package com.mycompany.safetyAlert.serviceDao;

import com.mycompany.safetyAlert.model.Person;
import com.mycompany.safetyAlert.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)

class PersonServiceDaoTest {
    Person personTest = new Person("Durand", "Emile", "2 rue Verte", "Lille", "58962", "55555555", "toto@gmail.com");

    @Autowired
    PersonServiceDao personServiceDao;

    @Autowired
    DataRepository dataRepository;

    @BeforeEach
    void init (){
        dataRepository.init();
        dataRepository.setCommit(false);
    }

    @Test
    void createPerson() {
        // Est ce que la méthode renvoie True ?
        assertThat(personServiceDao.createPerson(personTest)).isTrue();
        // datarepository contient il le nouvel enregistrement ?
        assertThat(dataRepository.getAllPersons().contains(personTest)).isTrue();
    }

    @Test
    void deletePerson() {
        // L'enregistrement n'existe pas, la méthode doit renvoyer False
        assertThat(personServiceDao.deletePerson(personTest)).isFalse();
        // On cré l'enregistrement
        personServiceDao.createPerson(personTest);
        // On vérifie que l'enregistrement a bien été ajouté
        assertThat(dataRepository.getAllPersons().contains(personTest)).isTrue();
        // La méthode doit maintenant renvoyer True
        assertThat(personServiceDao.deletePerson(personTest)).isTrue();
        // On vérifie que l'enregistrement a bien été supprimé
        assertThat(dataRepository.getAllPersons().contains(personTest)).isFalse();
    }

    @Test
    void updatePerson() {
        // L'enregistrement n'existe pas, la méthode doit renvoyer False
        assertThat(personServiceDao.updatePerson(personTest)).isFalse();
        // On cré l'enregistrement
        personServiceDao.createPerson(personTest);
        // La méthode doit maintenant renvoyer True
        assertThat(personServiceDao.updatePerson(personTest)).isTrue();
    }
}