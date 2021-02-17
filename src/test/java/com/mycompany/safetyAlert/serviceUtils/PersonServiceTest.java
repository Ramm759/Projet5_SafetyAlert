package com.mycompany.safetyAlert.serviceUtils;

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
public class PersonServiceTest {
    @Autowired
    PersonService personServiceTest;
    @MockBean
    PersonServiceDao personServiceDaoTest;
    @MockBean
    MedicalrecordService medicalrecordServiceTest;
    @MockBean
    MedicalrecordServiceDao medicalrecordServiceDao;
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
    Person durant = new Person("Dominique", "DURANT", "2 rue verte", "Lille", "59000", "2155455455", "toto@gmail.com");
    Person dupuis = new Person("Alain", "DUPUIS", "2 rue verte", "Lille", "59000", "2155455455", "toto@gmail.com");

    PersonInfoWithoutPhone dupond2 = new PersonInfoWithoutPhone("Jacques", "DUPOND", "2 rue verte", 17, "toto@gmail.com", medication, allergies);

    Medicalrecord mrDupond = new Medicalrecord("Jacques", "DUPOND", "03/06/1989", medication, allergies);
    Medicalrecord mrDurand = new Medicalrecord("Dominique", "DURANT", "03/06/1989", medication, allergies);
    Medicalrecord mrDupuis = new Medicalrecord("Alain", "DUPUIS", "03/06/1989", medication, allergies);

    String cityOk = "Culver";
    String cityKo = "Lille";

    @Test
    public void createExistingPersonTest() throws Exception {
        // GIVEN
        List<Person> persons = new ArrayList<>();
        persons.add(dupond);

        // WHEN
        Mockito.when(dataRepository.getAllPersons()).thenReturn(persons);

        // THEN
        try {
            Assertions.assertFalse(personServiceTest.createPerson(dupond));

        } catch (DataAlreadyExistException daee) {
            // On vérifie le nombre d'appel au service (0)
            verify(personServiceDaoTest, Mockito.times(0)).createPerson(dupond);
            assert (daee.getMessage().contains("existe déjà."));
        }
    }

    @Test
    public void createNonExistingPersonTest() throws Exception {
        // GIVEN
        List<Person> persons = new ArrayList<>();

        // WHEN
        Mockito.when(personUtils.getListPersons()).thenReturn(persons);

        // THEN
        Assertions.assertTrue(personServiceTest.createPerson(dupond));
        verify(personServiceDaoTest, Mockito.times(1)).createPerson(dupond);
    }

    @Test
    public void updateExistingPersonTest() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(personServiceDaoTest.updatePerson(any(Person.class))).thenReturn(true);

        // THEN
        Assertions.assertTrue(personServiceTest.updatePerson(dupond));
        verify(personServiceDaoTest, Mockito.times(1)).updatePerson(dupond);
    }

    @Test
    public void updateNonExistingPersonTest() throws Exception {
        //GIVEN

        // WHEN
        Mockito.when(personServiceDaoTest.updatePerson(any(Person.class))).thenReturn(false);

        //THEN
        try {
            Assertions.assertTrue(personServiceTest.updatePerson(dupond));
            verify(personServiceDaoTest, Mockito.times(0)).updatePerson(dupond);

        } catch (DataNotFoundException dnfe) {
            assert (dnfe.getMessage().contains("n'existe pas"));
        }
    }

    @Test
    public void deleteExistingPersonTest() throws Exception {
        // GIVEN

        // WHEN
        Mockito.when(personServiceDaoTest.deletePerson(any(Person.class))).thenReturn(true);

        // THEN
        Assertions.assertTrue(personServiceTest.deletePerson(dupond));
        verify(personServiceDaoTest, Mockito.times(1)).deletePerson(dupond);
    }

    @Test
    public void deleteNonExistingPersonTest() throws Exception {
        //GIVEN

        // WHEN
        Mockito.when(personServiceDaoTest.deletePerson(any(Person.class))).thenReturn(false);

        //THEN
        try {
            Assertions.assertTrue(personServiceTest.deletePerson(dupond));
            verify(personServiceDaoTest, Mockito.times(0)).deletePerson(dupond);

        } catch (DataNotFoundException dnfe) {
            assert (dnfe.getMessage().contains("n'existe pas"));
        }
    }

    @Test
    public void getPersonTest() throws Exception {
        // GIVEN
        List<Person> persons = new ArrayList<>();
        persons.add(dupond);
        persons.add(dupuis);

        // WHEN
        Mockito.when(personUtils.getListPersons()).thenReturn(persons);

        // THEN
        assertThat(personUtils.getListPersons().size()).isEqualTo(2);
        verify(personUtils, Mockito.times(1)).getListPersons();
    }

    @Test
    @Disabled
    public void getValidCommunityEmailTest() throws Exception {
        // GIVEN
        List<Person> persons = new ArrayList<>();
        persons.add(dupond);

        // WHEN
        Mockito.when(personUtils.getListPersons()).thenReturn(persons);

        // THEN
        assertThat(personUtils.getListPersons().size()).isEqualTo(2);
        verify(personUtils, Mockito.times(1)).getListPersons();
    }

    @Test
    public void getInvalidCommunityEmailTest() throws Exception {
        // GIVEN
        List<String> emails = new ArrayList<String>();

        // WHEN (any(String.class)) génere un String
        Mockito.when(personUtils.getCommunityEmail(any(String.class))).thenReturn(emails);

        // THEN
        assertThat(personUtils.getCommunityEmail(cityKo).size()).isEqualTo(0);
    }

    @Test
    public void getEmptyCityCommunityEmailTest() throws Exception {
        // GIVEN
        List<String> emails = new ArrayList<String>();

        // WHEN
        Mockito.when(personUtils.getCommunityEmail(any(String.class))).thenReturn(emails);

        // THEN
        try {
            assertThat(personUtils.getCommunityEmail("").size()).isEqualTo(0);
        } catch (InvalidArgumentException iae) {
            assert (iae.getMessage().contains("ne peut etre vidddddde"));
        }
    }

    @Test
    @Disabled
    public void getValidPersonInfoTest() throws Exception {
        // GIVEN
        List<PersonInfoWithoutPhone> persons = new ArrayList<>();
        List<PersonInfoWithoutPhone> personsTest = new ArrayList<>();
        persons.add(dupond2);

        List<Medicalrecord> medicalrecords = new ArrayList<>();
        medicalrecords.add(mrDupond);

        // WHEN
        Mockito.when(dataRepository.getMedicalrecordByName(any(String.class), any(String.class))).thenReturn(mrDupond);
        persons = (List<PersonInfoWithoutPhone>) personUtils.getPersonInfo(mrDupond.getLastName(), mrDupond.getFirstName());

        // THEN
        assertThat(personUtils.getPersonInfo(mrDupond.getLastName(), mrDupond.getFirstName())).isEqualTo(personsTest);
        verify(dataRepository, Mockito.times(1)).getMedicalrecordByName(any(String.class), any(String.class));
    }

    @Test
    public void getInvalidPersonInfoTest() throws Exception {
        // GIVEN


    }

    @Test
    public void getEmptyPersonInfoTest() throws Exception {

    }

    @Test
    public void getValidChildByAddress() throws Exception {

    }

    @Test
    public void getInvalidChildByAddress() throws Exception {

    }

    @Test
    public void getEmptyChildByAddress() throws Exception {

    }

}
