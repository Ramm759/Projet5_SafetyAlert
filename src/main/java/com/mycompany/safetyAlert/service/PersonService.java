package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.dto.PersonInfo;
import com.mycompany.safetyAlert.dto.PersonInfoWithFirestationNb;
import com.mycompany.safetyAlert.dto.PersonInfoWithMinor;
import com.mycompany.safetyAlert.model.Medicalrecord;
import com.mycompany.safetyAlert.model.Person;
import com.mycompany.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class PersonService implements IPersonService {
    @Autowired
    private DataRepository dataRepository;

    @Override
    public Collection<PersonInfoWithMinor> getPersonInfo(int stationNumber) {
        Collection<PersonInfoWithMinor> personInfoCollection = new ArrayList<>();
        int nbMineurs = 0;
        int nbMajeurs = 0;

        // Pour chaque adresse correspondant à stationNumber
        for (String address : dataRepository.getAddressByIdStation(stationNumber)) {

            // On cherche les personnes correspondantes
            for (Person person : dataRepository.getPersonByAddress(address)) {
                PersonInfoWithMinor personInfo = new PersonInfoWithMinor();
                personInfo.setFirstName(person.getFirstName());
                personInfo.setLastName(person.getLastName());
                personInfo.setAddress(person.getAddress());
                personInfo.setPhone(person.getPhone());

                Medicalrecord medicalrecord = dataRepository.getMedicalrecordByName(person.getLastName(), person.getFirstName());

                int age = dataRepository.calculateAge(medicalrecord.getBirthdate());
                personInfo.setAge(age);

                if (age <= 18) {
                    nbMineurs++;
                } else nbMajeurs++;

                personInfo.setNbMajeurs(nbMajeurs);
                personInfo.setNbMineurs(nbMineurs);

                personInfoCollection.add(personInfo);
            }
            // Mise à jour du nombre de Mineurs / majeurs
            for (PersonInfoWithMinor personInfo : personInfoCollection) {
                personInfo.setNbMajeurs(nbMajeurs);
                personInfo.setNbMineurs(nbMineurs);
            }
        }
        return personInfoCollection;
    }

    @Override
    public Collection<PersonInfo> getChildrenByAddress(String address) {
        Collection<PersonInfo> personInfoCollection = new ArrayList<>();
        int nbMineurs = 0;
        int nbMajeurs = 0;
        for (Person person : dataRepository.getPersonByAddress(address)) {
            PersonInfo personInfo = new PersonInfo();
            personInfo.setFirstName(person.getFirstName());
            personInfo.setLastName(person.getLastName());
            personInfo.setAddress(person.getAddress());
            personInfo.setPhone(person.getPhone());

            Medicalrecord medicalrecord = dataRepository.getMedicalrecordByName(person.getLastName(), person.getFirstName());

            int age = dataRepository.calculateAge(medicalrecord.getBirthdate());
            personInfo.setAge(age);

            if (age <= 18) {
                nbMineurs++;
            } else nbMajeurs++;

            personInfoCollection.add(personInfo);
        }
        if (nbMineurs == 0) {
            personInfoCollection.clear();
        }
        return personInfoCollection;
    }

    @Override
    public Collection<String> getCommunityPhone(int stationNumber) {
        Collection<String> collectionPhones = new HashSet<>();

        // Pour chaque adresse correspondant à stationNumber
        for (String address : dataRepository.getAddressByIdStation(stationNumber)) {
            // On cherche les personnes correspondantes
            for (Person person : dataRepository.getPersonByAddress(address)) {
                collectionPhones.add(person.getPhone());
            }
        }
        return collectionPhones;
    }

    @Override
    public Collection<PersonInfoWithFirestationNb> getPersonnsByAdress(String address) {

        Collection<PersonInfoWithFirestationNb> personInfoCollection = new ArrayList<>();
        for (Person person : dataRepository.getPersonByAddress(address)) {
            PersonInfoWithFirestationNb personInfo = new PersonInfoWithFirestationNb();
            personInfo.setFirstName(person.getFirstName());
            personInfo.setLastName(person.getLastName());
            personInfo.setPhone(person.getPhone());

            Medicalrecord medicalrecord = dataRepository.getMedicalrecordByName(person.getLastName(), person.getFirstName());

            int age = dataRepository.calculateAge(medicalrecord.getBirthdate());
            personInfo.setAge(age);
            personInfo.setMedications(medicalrecord.getMedications());
            personInfo.setAllergies(medicalrecord.getAllergies());

            // Recherche de la caserne
            personInfo.setFirestationNb(dataRepository.getStationNumber(address));

            personInfoCollection.add(personInfo);
        }
        return personInfoCollection;
    }

    @Override
    public Collection<PersonInfo> getPersonInfo(String lastName, String firstName) {
        Collection<PersonInfo> personInfoCollection = new ArrayList<>();
        List<Person> personList = dataRepository.listPersonnByName(lastName, firstName);
        for (Person person : personList) {
            PersonInfo personInfo = new PersonInfo();
            personInfo.setFirstName(person.getFirstName());
            personInfo.setLastName(person.getLastName());
            personInfo.setAddress(person.getAddress());
            personInfo.setEmail(person.getEmail());

            Medicalrecord medicalrecord = dataRepository.getMedicalrecordByName(person.getLastName(), person.getFirstName());

            int age = dataRepository.calculateAge(medicalrecord.getBirthdate());

            personInfo.setAge(age);
            personInfo.setMedications(medicalrecord.getMedications());
            personInfo.setAllergies(medicalrecord.getAllergies());

            personInfoCollection.add(personInfo);
        }
        return personInfoCollection;
    }

    @Override
    public Collection<String> getCommunityEmail(String city) {
        Collection<String> collectionEmails = new HashSet<String>();

        for (Person person : dataRepository.getPersonByCity(city)) {
            collectionEmails.add(person.getEmail());
        }
        return collectionEmails;
    }

    @Override
    public Collection<PersonInfo> getPersonnsByIdStation(int[] idStationList) {
        int nbMineurs = 0;
        int nbMajeurs = 0;
        Collection<PersonInfo> personInfoCollection = new ArrayList<>();

        for (int idStation : idStationList) {

            // Pour chaque adresse correspondant à stationNumber
            for (String address : dataRepository.getAddressByIdStation(idStation)) {
                // On cherche les personnes correspondantes
                for (Person person : dataRepository.getPersonByAddress(address)) {
                    PersonInfo personInfo = new PersonInfo();
                    personInfo.setLastName(person.getLastName());
                    personInfo.setFirstName(person.getFirstName());
                    personInfo.setPhone(person.getPhone());

                    Medicalrecord medicalrecord = dataRepository.getMedicalrecordByName(person.getLastName(), person.getFirstName());
                    int age = dataRepository.calculateAge(medicalrecord.getBirthdate());

                    personInfo.setAge(age);

                    if (age <= 18) {
                        nbMineurs++;
                    } else nbMajeurs++;

                    personInfo.setMedications(medicalrecord.getMedications());
                    personInfo.setAllergies(medicalrecord.getAllergies());

                    personInfoCollection.add(personInfo);
                }
            }
        }
        return  personInfoCollection;
    }
}


