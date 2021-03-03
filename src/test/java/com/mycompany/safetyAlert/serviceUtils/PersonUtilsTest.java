package com.mycompany.safetyAlert.serviceUtils;

import com.mycompany.safetyAlert.dto.PersonInfoForChildAlert;
import com.mycompany.safetyAlert.dto.PersonInfoForFlood;
import com.mycompany.safetyAlert.dto.PersonInfoWithMinor;
import com.mycompany.safetyAlert.dto.PersonInfoWithoutPhone;
import com.mycompany.safetyAlert.model.Medicalrecord;
import com.mycompany.safetyAlert.model.Person;
import com.mycompany.safetyAlert.repository.DataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PersonUtilsTest {
    @Autowired
    IPersonUtils personUtilsTest;
    @MockBean
    DataRepository dataRepository;

    Person obama = new Person("Barack", "obama", "WhiteHouse", "Washinton DC", "1232111","06755" , "obama@president.com");
    Person biden = new Person("joe", "biden", "BlackHouse", "Washinton DC", "1232111","06754" , "biden@president.com");
    Person trump = new Person("Donald", "trump", "RedHouse", "Washinton DC", "1232111", "06753","trump@president.com");
    Person mineur = new Person("Chloe", "Johanson", "BlueHouse", "Washinton DC", "1232111","06755" , "mineur@president.com");

    List<String> medication = List.of("a,b,c,d");
    List<String> allergies = List.of("q,s,d,d");

    Medicalrecord medicalrecordObama = new Medicalrecord("Barack", "obama","03/06/1984",medication, allergies);
    Medicalrecord medicalrecordMineur = new Medicalrecord("Chloe", "Johanson","03/06/2020",medication, allergies);

    String stationNumber = "1";
    String addressMajeur = "WhiteHouse";
    String addressMineur = "BlueHouse";
    String addressDefaut = "DefaultHouse";

    @Test
    void getPersonInfoWithMajeur() {
        // GIVEN
        Mockito.when(dataRepository.getAddressByIdStation(stationNumber)).thenReturn(List.of(obama.getAddress()));
        Mockito.when(dataRepository.getPersonByAddress(obama.getAddress())).thenReturn(List.of(obama));
        Mockito.when(dataRepository.getMedicalrecordByName(obama.getLastName(),obama.getFirstName())).thenReturn(medicalrecordObama);
        Mockito.when(dataRepository.calculateAge(medicalrecordObama.getBirthdate())).thenReturn(30);

        // WHEN
        Collection<PersonInfoWithMinor> persons = personUtilsTest.getPersonInfo(stationNumber);

        // THEN
        assertThat(persons).hasSize(1); // retourne 1 personne
        PersonInfoWithMinor personResult = persons.iterator().next(); // retourne le prochain élément (le seul)
        assertThat(personResult.getFirstName()).isEqualTo("Barack");
        assertThat(personResult.getLastName()).isEqualTo("obama");
        assertThat(personResult.getAddress()).isEqualTo("WhiteHouse");
        assertThat(personResult.getAge()).isEqualTo(30);
        assertThat(personResult.getPhone()).isEqualTo("06755");
        assertThat(personResult.getNbMajeurs()).isEqualTo(1);
        assertThat(personResult.getNbMineurs()).isEqualTo(0);
    }

    @Test
    void getPersonInfoWithMineur() {
        // GIVEN
        Mockito.when(dataRepository.getAddressByIdStation(stationNumber)).thenReturn(List.of(mineur.getAddress()));
        Mockito.when(dataRepository.getPersonByAddress(mineur.getAddress())).thenReturn(List.of(mineur));
        Mockito.when(dataRepository.getMedicalrecordByName(mineur.getLastName(),mineur.getFirstName())).thenReturn(medicalrecordMineur);
        Mockito.when(dataRepository.calculateAge(medicalrecordMineur.getBirthdate())).thenReturn(10);

        // WHEN
        Collection<PersonInfoWithMinor> persons = personUtilsTest.getPersonInfo(stationNumber);

        // THEN
        assertThat(persons).hasSize(1); // retourne 1 personne
        PersonInfoWithMinor personResult = persons.iterator().next(); // retourne le prochain élément (le seul)
        assertThat(personResult.getFirstName()).isEqualTo("Chloe");
        assertThat(personResult.getLastName()).isEqualTo("Johanson");
        assertThat(personResult.getAddress()).isEqualTo("BlueHouse");
        assertThat(personResult.getAge()).isEqualTo(10);
        assertThat(personResult.getPhone()).isEqualTo("06755");
        assertThat(personResult.getNbMajeurs()).isEqualTo(0);
        assertThat(personResult.getNbMineurs()).isEqualTo(1);
    }

    @Test
    void getChildrenByAddressMineur() {
        // GIVEN
        Mockito.when(dataRepository.getMedicalrecordByName(mineur.getLastName(), mineur.getFirstName())).thenReturn(medicalrecordMineur);
        Mockito.when(dataRepository.getPersonByAddress(mineur.getAddress())).thenReturn(List.of(mineur));
        Mockito.when(dataRepository.calculateAge(medicalrecordMineur.getBirthdate())).thenReturn(10);

        // WHEN
        Collection<PersonInfoForChildAlert> persons = personUtilsTest.getChildrenByAddress(addressMineur);

        // THEN
        assertThat(persons).hasSize(1); // retourne 1 personne
        PersonInfoForChildAlert personResult = persons.iterator().next() ; // retourne le prochain élément (le seul)
        assertThat(personResult.getFirstName()).isEqualTo("Chloe");
        assertThat(personResult.getLastName()).isEqualTo("Johanson");
        assertThat(personResult.getAddress()).isEqualTo("BlueHouse");
        assertThat(personResult.getAge()).isEqualTo(10);
    }

    @Test
    void getChildrenByAddressMajeur() {
        // GIVEN
        Mockito.when(dataRepository.getMedicalrecordByName(obama.getLastName(), obama.getFirstName())).thenReturn(medicalrecordObama);
        Mockito.when(dataRepository.getPersonByAddress(obama.getAddress())).thenReturn(List.of(obama));
        Mockito.when(dataRepository.calculateAge(medicalrecordObama.getBirthdate())).thenReturn(30);

        // WHEN
        Collection<PersonInfoForChildAlert> persons = personUtilsTest.getChildrenByAddress(addressMajeur);

        // THEN
        assertThat(persons).hasSize(0); // retourne 0 personne

    }

    @Test
    void getCommunityPhone() {
        // GIVEN
        Mockito.when(dataRepository.getAddressByIdStation(stationNumber)).thenReturn(List.of(addressMajeur));
        Mockito.when(dataRepository.getPersonByAddress(addressMajeur)).thenReturn(List.of(obama));

        // WHEN
        Collection<String>phones = personUtilsTest.getCommunityPhone(stationNumber);

        // THEN
        assertThat(phones).hasSize(1);
        String phoneObama = phones.iterator().next();
        assertThat(phoneObama).isEqualTo("06755");
    }

    @Test
    void getPersonnsByAdress() {
    }

    @Test
    void testGetPersonInfo() {
        // GIVEN
        Mockito.when(dataRepository.listPersonnByName(obama.getLastName(), obama.getFirstName())).thenReturn(List.of(obama));
        Mockito.when(dataRepository.getMedicalrecordByName(obama.getLastName(), obama.getFirstName())).thenReturn(medicalrecordObama);
        Mockito.when(dataRepository.calculateAge(medicalrecordObama.getBirthdate())).thenReturn(30);

        // WHEN
        Collection<PersonInfoWithoutPhone> personInfo = personUtilsTest.getPersonInfo(obama.getLastName(), obama.getFirstName());

        // THEN
        assertThat(personInfo).hasSize(1);
        PersonInfoWithoutPhone next = personInfo.iterator().next();
        assertThat(next.getFirstName()).isEqualTo("Barack");
        assertThat(next.getLastName()).isEqualTo("obama");
        assertThat(next.getAddress()).isEqualTo("WhiteHouse");
        assertThat(next.getAge()).isEqualTo(30);
        assertThat(next.getEmail()).isEqualTo("obama@president.com");

    }

    @Test
    void getCommunityEmail() {
    }

    @Test
    void getPersonnsByIdStation() {
        // GIVEN
        Mockito.when(dataRepository.getAddressByIdStation(stationNumber)).thenReturn(List.of(addressMajeur));
        Mockito.when(dataRepository.getPersonByAddress(addressMajeur)).thenReturn(List.of(obama));
        Mockito.when(dataRepository.getMedicalrecordByName(obama.getLastName(), obama.getFirstName())).thenReturn(medicalrecordObama);
        Mockito.when(dataRepository.calculateAge(medicalrecordObama.getBirthdate())).thenReturn(30);

        // WHEN
        String[] stationArray = {stationNumber};
        Collection<PersonInfoForFlood> personInfoCollection = personUtilsTest.getPersonnsByIdStation(stationArray);

        // THEN
        assertThat(stationArray).hasSize(1);
        PersonInfoForFlood personInfoForFlood = personInfoCollection.iterator().next();
        assertThat(personInfoForFlood.getFirstName()).isEqualTo("Barack");
        assertThat(personInfoForFlood.getLastName()).isEqualTo("obama");
        assertThat(personInfoForFlood.getPhone()).isEqualTo("06755");
        assertThat(personInfoForFlood.getAge()).isEqualTo(30);
    }

    @Test
    void getListPersons() {
    }
}