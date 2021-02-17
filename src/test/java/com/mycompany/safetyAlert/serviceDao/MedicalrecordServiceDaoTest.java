package com.mycompany.safetyAlert.serviceDao;

import com.mycompany.safetyAlert.model.Medicalrecord;
import com.mycompany.safetyAlert.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class MedicalrecordServiceDaoTest {
    List<String> medications = new ArrayList<String>(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
    List<String> allergies = new ArrayList<String>(Arrays.asList("nillacilan", "hydrapermazol:100mg"));
    Medicalrecord medicalrecordTest = new Medicalrecord("Henri", " Dupon", "03/06/1984", medications, allergies);

    @Autowired
    MedicalrecordServiceDao medicalrecordServiceDao;

    @Autowired
    DataRepository dataRepository;

    @BeforeEach
    void init() {
        dataRepository.init();
        dataRepository.setCommit(false);
    }

    @Test
    void createMedicalrecord() {
        // Est ce que la méthode renvoie True ?
        assertThat(medicalrecordServiceDao.createMedicalrecord(medicalrecordTest)).isTrue();
        // datarepository contient il le nouvel enregistrement ?
        assertThat(dataRepository.getAllMedicalRecord().contains(medicalrecordTest)).isTrue();
    }

    @Test
    void deleteMedicalrecord() {
        // L'enregistrement n'existe pas, la méthode doit renvoyer False
        assertThat(medicalrecordServiceDao.deleteMedicalrecord(medicalrecordTest)).isFalse();
        // On cré l'enregistrement
        medicalrecordServiceDao.createMedicalrecord(medicalrecordTest);
        // On vérifie que l'enregistrement a bien été ajouté
        assertThat(dataRepository.getAllMedicalRecord().contains(medicalrecordTest)).isTrue();
        // La méthode doit maintenant renvoyer True
        assertThat(medicalrecordServiceDao.deleteMedicalrecord(medicalrecordTest)).isTrue();
        // On vérifie que l'enregistrement a bien été supprimé
        assertThat(dataRepository.getAllMedicalRecord().contains(medicalrecordTest)).isFalse();
    }

    @Test
    void updateMedicalrecord() {
        // L'enregistrement n'existe pas, la méthode doit renvoyer False
        assertThat(medicalrecordServiceDao.updateMedicalrecord(medicalrecordTest)).isFalse();
        // On cré l'enregistrement
        medicalrecordServiceDao.createMedicalrecord(medicalrecordTest);
        // La méthode doit maintenant renvoyer True
        assertThat(medicalrecordServiceDao.updateMedicalrecord(medicalrecordTest)).isTrue();
    }
}