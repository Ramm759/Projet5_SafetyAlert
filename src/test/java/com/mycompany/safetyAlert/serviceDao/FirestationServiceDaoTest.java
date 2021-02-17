package com.mycompany.safetyAlert.serviceDao;


import com.mycompany.safetyAlert.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mycompany.safetyAlert.model.Firestation;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class FirestationServiceDaoTest {

    Firestation firestationTest = new Firestation("Culver", "3");

    @Autowired
    FirestationServiceDao firestationServiceDao;

    @Autowired
    DataRepository dataRepository;

    @BeforeEach
    void init() {
        dataRepository.init();
        dataRepository.setCommit(false);
    }

    @Test
    void createFirestation() {
        // Est ce que la méthode renvoie True ?
        assertThat(firestationServiceDao.createFirestation(firestationTest)).isTrue();
        // datarepository contient il le nouvel enregistrement ?
        assertThat(dataRepository.getAllFirestations().contains(firestationTest)).isTrue();
    }

    @Test
    void deleteFirestation() {
        // L'enregistrement n'existe pas, la méthode doit renvoyer False
        assertThat(firestationServiceDao.deleteFirestation(firestationTest)).isFalse();
        // On cré l'enregistrement
        firestationServiceDao.createFirestation(firestationTest);
        // On vérifie que l'enregistrement a bien été ajouté
        assertThat(dataRepository.getAllFirestations().contains(firestationTest)).isTrue();
        // La méthode doit maintenant renvoyer True
        assertThat(firestationServiceDao.deleteFirestation(firestationTest)).isTrue();
        // On vérifie que l'enregistrement a bien été supprimé
        assertThat(dataRepository.getAllFirestations().contains(firestationTest)).isFalse();
    }

    @Test
    void updateFirestation() {
        // L'enregistrement n'existe pas, la méthode doit renvoyer False
        assertThat(firestationServiceDao.updateFirestation(firestationTest)).isFalse();
        // On cré l'enregistrement
        firestationServiceDao.createFirestation(firestationTest);
        // La méthode doit maintenant renvoyer True
        assertThat(firestationServiceDao.updateFirestation(firestationTest)).isTrue();
    }
}