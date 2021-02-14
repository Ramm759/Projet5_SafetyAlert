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

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class PersonServiceDaoTest {

    Person durand = new Person("Durand", "Emile", "2 rue Verte", "Lille", "58962", "55555555", "toto@gmail.com");

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
        assertThat(personServiceDao.createPerson(durand)).isTrue();
        assertThat(dataRepository.getAllPersons().contains(durand)).isTrue();

    }

    @Test
    void deletePerson() {
        assertThat(personServiceDao.deletePerson(durand)).isFalse();
        personServiceDao.createPerson(durand);
        assertThat(dataRepository.getAllPersons().contains(durand)).isTrue();
        assertThat(personServiceDao.deletePerson(durand)).isTrue();
        assertThat(dataRepository.getAllPersons().contains(durand)).isFalse();
    }

    @Test
    void updatePerson() {
        assertThat(personServiceDao.updatePerson(durand)).isFalse();
        personServiceDao.createPerson(durand);
        assertThat(personServiceDao.updatePerson(durand)).isTrue();

    }
}