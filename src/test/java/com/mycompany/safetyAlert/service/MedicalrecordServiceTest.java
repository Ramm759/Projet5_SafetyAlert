package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.dto.PersonInfoWithoutPhone;
import com.mycompany.safetyAlert.exceptions.DataAlreadyExistException;
import com.mycompany.safetyAlert.exceptions.DataNotFoundException;
import com.mycompany.safetyAlert.exceptions.InvalidArgumentException;
import com.mycompany.safetyAlert.model.Medicalrecord;
import com.mycompany.safetyAlert.model.Person;
import com.mycompany.safetyAlert.repository.DataRepository;
import com.mycompany.safetyAlert.service.MedicalrecordService;
import com.mycompany.safetyAlert.service.PersonService;
import com.mycompany.safetyAlert.serviceDao.MedicalrecordServiceDao;
import com.mycompany.safetyAlert.serviceDao.PersonServiceDao;
import com.mycompany.safetyAlert.serviceUtils.PersonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

public class MedicalrecordServiceTest {
    @Autowired
    MedicalrecordService medicalrecordServiceTest;
    @MockBean
    MedicalrecordServiceDao medicalrecordServiceDaoTest;
    @MockBean
    DataNotFoundException dataNotFoundException;
    @MockBean
    InvalidArgumentException invalidArgumentException;
    @MockBean
    PersonUtils personUtils;
    @MockBean
    DataRepository dataRepository;

    List<String> medication = new ArrayList<>(Arrays.asList("a", "b", "c"));
    List<String> allergies = new ArrayList<>(Arrays.asList("a", "b", "c"));
    Person dupond = new Person("Jacques", "DUPOND", "2 rue verte", "Lille", "59000", "2155455455", "toto@gmail.com");

    Medicalrecord medicalrecordTest = new Medicalrecord("Jacques", "DUPOND", "03/06/1989", medication, allergies);

    @Test
    public void createExistingMedicalrecordTest() throws Exception {
        // GIVEN

        List<Medicalrecord> medicalrecords = new ArrayList<>();
        medicalrecords.add(medicalrecordTest);

        // WHEN
        Mockito.when(dataRepository.getAllMedicalRecord()).thenReturn(medicalrecords);
        Mockito.when(dataRepository.getAllPersons()).thenReturn((List.of(dupond)));

        // THEN
        try {
            Assertions.assertFalse(medicalrecordServiceTest.createMedicalrecord(medicalrecordTest));

        } catch (DataAlreadyExistException daee) {
            // On vérifie le nombre d'appel au service (0)
            verify(medicalrecordServiceDaoTest, Mockito.times(0)).createMedicalrecord(medicalrecordTest);
            assert (daee.getMessage().contains("existe déjà."));
        }
    }

    @Test
    public void createExistingMedicalrecordTestWhenPersonNotExist() throws Exception {
        // GIVEN

        List<Medicalrecord> medicalrecords = new ArrayList<>();
        medicalrecords.add(medicalrecordTest);

        // WHEN
        Mockito.when(dataRepository.getAllMedicalRecord()).thenReturn(medicalrecords);
        Mockito.when(dataRepository.getAllPersons()).thenReturn((List.of()));

        // THEN
        try {
            Assertions.assertFalse(medicalrecordServiceTest.createMedicalrecord(medicalrecordTest));

        } catch (DataNotFoundException dnfe) {
            // On vérifie le nombre d'appel au service (0)
            verify(medicalrecordServiceDaoTest, Mockito.times(0)).createMedicalrecord(medicalrecordTest);
            assert (dnfe.getMessage().contains("La personne"));
        }
    }

    @Test
    public void createNonExistingMedicalrecorTest() throws Exception {
        // GIVEN
        List<Medicalrecord> medicalrecords = new ArrayList<>();

        // WHEN
        Mockito.when(dataRepository.getAllMedicalRecord()).thenReturn(medicalrecords);
        Mockito.when(dataRepository.getAllPersons()).thenReturn((List.of(dupond)));

        // THEN
        Assertions.assertTrue(medicalrecordServiceTest.createMedicalrecord(medicalrecordTest));
        verify(medicalrecordServiceDaoTest, Mockito.times(1)).createMedicalrecord(medicalrecordTest);
    }

    @Test
    public void updateExistingMedicalrecordTest() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(medicalrecordServiceDaoTest.updateMedicalrecord(any(Medicalrecord.class))).thenReturn(true);

        // THEN
        Assertions.assertTrue(medicalrecordServiceTest.updateMedicalrecord(medicalrecordTest));
        verify(medicalrecordServiceDaoTest, Mockito.times(1)).updateMedicalrecord(medicalrecordTest);
    }

    @Test
    public void updateNonExistingMedicalrecordTest() throws Exception {
        //GIVEN

        // WHEN
        Mockito.when(medicalrecordServiceDaoTest.updateMedicalrecord(any(Medicalrecord.class))).thenReturn(false);

        //THEN
        try {
            Assertions.assertTrue(medicalrecordServiceTest.updateMedicalrecord(medicalrecordTest));

        } catch (DataNotFoundException dnfe) {
            verify(medicalrecordServiceDaoTest, Mockito.times(1)).updateMedicalrecord(medicalrecordTest);
            assert (dnfe.getMessage().contains("n'existe pas"));
        }
    }

    @Test
    public void deleteExistingMedicalrecordTest() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(medicalrecordServiceDaoTest.deleteMedicalrecord(any(Medicalrecord.class))).thenReturn(true);

        // THEN
        Assertions.assertTrue(medicalrecordServiceTest.deleteMedicalrecord(medicalrecordTest));
        verify(medicalrecordServiceDaoTest, Mockito.times(1)).deleteMedicalrecord(medicalrecordTest);
    }

    @Test
    public void deleteNonExistingMedicalrecordTest() throws Exception {
        //GIVEN

        // WHEN
        Mockito.when(medicalrecordServiceDaoTest.deleteMedicalrecord(any(Medicalrecord.class))).thenReturn(false);

        //THEN
        try {
            Assertions.assertTrue(medicalrecordServiceTest.deleteMedicalrecord(medicalrecordTest));

        } catch (DataNotFoundException dnfe) {
            verify(medicalrecordServiceDaoTest, Mockito.times(1)).deleteMedicalrecord(medicalrecordTest);
            assert (dnfe.getMessage().contains("n'existe pas"));
        }
    }
}
