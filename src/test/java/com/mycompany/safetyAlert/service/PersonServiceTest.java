package com.mycompany.safetyAlert.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.mycompany.safetyAlert.dao.MedicalrecordDaoImpl;
import com.mycompany.safetyAlert.dao.PersonDao;
import com.mycompany.safetyAlert.dao.PersonDaoImpl;
import com.mycompany.safetyAlert.exceptions.DataAlreadyExistException;
import com.mycompany.safetyAlert.exceptions.DataNotFoundException;
import com.mycompany.safetyAlert.exceptions.InvalidArgumentException;
import com.mycompany.safetyAlert.model.Medicalrecord;
import com.mycompany.safetyAlert.model.Person;
import com.mycompany.safetyAlert.serviceDao.MedicalrecordServiceDao;
import com.mycompany.safetyAlert.serviceDao.PersonServiceDao;
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
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonServiceTest {
    @Autowired
    PersonDaoImpl personServiceTest;
    @MockBean
    PersonServiceDao personServiceDaoTest;
    @MockBean
    MedicalrecordDaoImpl medicalrecordServiceTest;
    @MockBean
    MedicalrecordServiceDao medicalrecordServiceDao;
    @MockBean
    DataNotFoundException dataNotFoundException;
    @MockBean
    InvalidArgumentException invalidArgumentException;
    @MockBean
    PersonService personService;


    Person dupond = new Person("Jacques", "DUPOND", "2 rue verte", "Lille", "59000", "2155455455", "toto@gmail.com");
    Person durant = new Person("Dominique", "DURANT", "2 rue verte", "Lille", "59000", "2155455455", "toto@gmail.com");
    Person dupuis = new Person("Alain", "DUPUIS", "2 rue verte", "Lille", "59000", "2155455455", "toto@gmail.com");

    List<String> medication = new ArrayList<>(Arrays.asList("a", "b", "c"));
    List<String> allergies = new ArrayList<>(Arrays.asList("a", "b", "c"));

    Medicalrecord mrDupond = new Medicalrecord("Jacques", "DUPOND", "03/06/1989", medication, allergies);
    Medicalrecord mrDurand = new Medicalrecord("Dominique", "DURANT", "03/06/1989", medication, allergies);
    Medicalrecord mrDupuis = new Medicalrecord("Alain", "DUPUIS", "03/06/1989", medication, allergies);

    @Test
    public void createExistingPersonTest()throws Exception{
        // GIVEN
        List<Person> persons = new ArrayList<>();
        persons.add(dupond);

        // WHEN
        Mockito.when(personService.getListPersons()).thenReturn(persons);

        // THEN
        try {
            Assertions.assertFalse(personServiceTest.createPerson(dupond));
            // On vérifie le nombre d'appel au service (0)
            verify(personServiceDaoTest,Mockito.times(0)).createPerson(any());
        }
        catch (DataAlreadyExistException daee) {
            assert(daee.getMessage().contains("existe déjà."));
        }



    }

    @Test
    public void createNonExistingPersonTest()throws Exception{
        // GIVEN
        List<Person> persons = new ArrayList<>();

        // WHEN
        Mockito.when(personService.getListPersons()).thenReturn(persons);

        // THEN
        Assertions.assertTrue(personServiceTest.createPerson(dupond));
        verify(personServiceDaoTest,Mockito.times(1)).createPerson(dupond);

    }

    @Test
    public void updateExistingPersonTest()throws Exception{
        // GIVEN

        // WHEN
        Mockito.when(personServiceDaoTest.updatePerson(any(Person.class))).thenReturn(true);

        // THEN
        Assertions.assertTrue(personServiceTest.updatePerson(dupond));
        verify(personServiceDaoTest,Mockito.times(1)).updatePerson(dupond);
    }

    @Test
    public void updateNonExistingPersonTest()throws Exception{

    }

    @Test
    public void deleteExistingPersonTest()throws Exception{

    }

    @Test
    public void deleteNonExistingPersonTest()throws Exception{

    }

    @Test
    public void getPersonTest()throws Exception{

    }

    @Test
    public void getValidCommunityEmailTest()throws Exception{

    }

    @Test
    public void getInvalidCommunityEmailTest()throws Exception{

    }

    @Test
    public void getEmptyCityCommunityEmailTest()throws Exception{

    }

    @Test
    public void getValidPersonInfoTest()throws Exception{

    }

    @Test
    public void getInvalidPersonInfoTest()throws Exception{

    }

    @Test
    public void getEmptyPersonInfoTest()throws Exception{

    }

    @Test
    public void getValidChildByAddress()throws Exception{

    }
    @Test
    public void getInvalidChildByAddress()throws Exception{

    }

    @Test
    public void getEmptyChildByAddress()throws Exception{

    }

}
