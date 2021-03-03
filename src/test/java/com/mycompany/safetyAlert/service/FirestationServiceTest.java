package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.exceptions.DataAlreadyExistException;
import com.mycompany.safetyAlert.exceptions.DataNotFoundException;
import com.mycompany.safetyAlert.exceptions.InvalidArgumentException;
import com.mycompany.safetyAlert.model.Firestation;
import com.mycompany.safetyAlert.repository.DataRepository;
import com.mycompany.safetyAlert.service.FirestationService;
import com.mycompany.safetyAlert.service.MedicalrecordService;
import com.mycompany.safetyAlert.serviceDao.FirestationServiceDao;
import com.mycompany.safetyAlert.serviceDao.MedicalrecordServiceDao;
import com.mycompany.safetyAlert.serviceUtils.PersonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FirestationServiceTest {
    @Autowired
    FirestationService firestationServiceTest;
    @MockBean
    FirestationServiceDao firestationServiceDaoTest;
    @MockBean
    DataNotFoundException dataNotFoundException;
    @MockBean
    InvalidArgumentException invalidArgumentException;
    @MockBean
    PersonUtils personUtils;
    @MockBean
    DataRepository dataRepository;

    Firestation firestationTest1 = new Firestation("villeTest1", "nb1");
    Firestation firestationTest2 = new Firestation("villeTest2", "nb2");

    @Test
    public void createExistingFirestationTest() throws Exception {
        // GIVEN
        List<Firestation> firestations = new ArrayList<>();
        firestations.add(firestationTest1);

        // WHEN
        Mockito.when(dataRepository.getAllFirestations()).thenReturn(firestations);

        // THEN
        try {
            Assertions.assertFalse(firestationServiceTest.createFirestation(firestationTest1));

        } catch (DataAlreadyExistException daee) {
            // On vérifie le nombre d'appel au service (0)
            verify(firestationServiceDaoTest, Mockito.times(0)).createFirestation(firestationTest1);
            assert (daee.getMessage().contains("existe déjà."));
        }
    }

    @Test
    public void createNonExistingFirestationTest() throws Exception {
        // GIVEN
        List<Firestation> firestations = new ArrayList<>();

        // WHEN
        Mockito.when(dataRepository.getAllFirestations()).thenReturn(firestations);

        // THEN
        Assertions.assertTrue(firestationServiceTest.createFirestation(firestationTest1));
        verify(firestationServiceDaoTest, Mockito.times(1)).createFirestation(firestationTest1);
    }

    @Test
    public void updateExistingFirestationTest() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(firestationServiceDaoTest.updateFirestation(any(Firestation.class))).thenReturn(true);

        // THEN
        Assertions.assertTrue(firestationServiceTest.updateFirestation(firestationTest1));
        verify(firestationServiceDaoTest, Mockito.times(1)).updateFirestation(firestationTest1);
    }

    @Test
    public void updateNonExistingFirestationTest() throws Exception {
        //GIVEN

        // WHEN
        Mockito.when(firestationServiceDaoTest.updateFirestation(any(Firestation.class))).thenReturn(false);

        //THEN
        try {
            Assertions.assertTrue(firestationServiceTest.updateFirestation(firestationTest1));

        } catch (DataNotFoundException dnfe) {
            verify(firestationServiceDaoTest, Mockito.times(1)).updateFirestation(firestationTest1);
            assert (dnfe.getMessage().contains("n'existe pas"));
        }
    }

    @Test
    public void deleteExistingFirestationTest() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(firestationServiceDaoTest.deleteFirestation(any(Firestation.class))).thenReturn(true);

        // THEN
        Assertions.assertTrue(firestationServiceTest.deleteFirestation(firestationTest1));
        verify(firestationServiceDaoTest, Mockito.times(1)).deleteFirestation(firestationTest1);
    }

    @Test
    public void deleteNonExistingFirestationTest() throws Exception {
        //GIVEN

        // WHEN
        Mockito.when(firestationServiceDaoTest.deleteFirestation(any(Firestation.class))).thenReturn(false);

        //THEN
        try {
            Assertions.assertTrue(firestationServiceTest.deleteFirestation(firestationTest1));

        } catch (DataNotFoundException dnfe) {
            verify(firestationServiceDaoTest, Mockito.times(1)).deleteFirestation(firestationTest1);
            assert (dnfe.getMessage().contains("n'existe pas"));
        }
    }
}
