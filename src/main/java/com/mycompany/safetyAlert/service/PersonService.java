package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.dto.*;
import com.mycompany.safetyAlert.exceptions.InvalidArgumentException;
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
    public Collection<PersonInfoWithMinor> getPersonInfo(String stationNumber) {
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
    public Collection<PersonInfoForChildAlert> getChildrenByAddress(String address) {
        Collection<PersonInfoForChildAlert> personInfoCollection = new ArrayList<>();
        int nbMineurs = 0;
        int nbMajeurs = 0;
        for (Person person : dataRepository.getPersonByAddress(address)) {
            PersonInfoForChildAlert personInfoForChildAlert = new PersonInfoForChildAlert();
            personInfoForChildAlert.setFirstName(person.getFirstName());
            personInfoForChildAlert.setLastName(person.getLastName());
            personInfoForChildAlert.setAddress(person.getAddress());

            Medicalrecord medicalrecord = dataRepository.getMedicalrecordByName(person.getLastName(), person.getFirstName());

            int age = dataRepository.calculateAge(medicalrecord.getBirthdate());
            personInfoForChildAlert.setAge(age);

            if (age <= 18) {
                nbMineurs++;
            } else nbMajeurs++;

            personInfoCollection.add(personInfoForChildAlert);
        }
        if (nbMineurs == 0) {
            personInfoCollection.clear();
        }
        return personInfoCollection;
    }

    @Override
    public Collection<String> getCommunityPhone(String stationNumber) {
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
    public Collection<PersonInfoWithoutPhone> getPersonInfo(String lastName, String firstName) {
        Collection<PersonInfoWithoutPhone> personInfoCollection = new ArrayList<>();
        List<Person> personList = dataRepository.listPersonnByName(lastName, firstName);
        for (Person person : personList) {
            PersonInfoWithoutPhone personInfo = new PersonInfoWithoutPhone();
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

        if (city.isEmpty()){
            throw new InvalidArgumentException("La ville ne peut être vide");
        }

        for (Person person : dataRepository.getPersonByCity(city)) {
            collectionEmails.add(person.getEmail());
        }
        return collectionEmails;
    }

    @Override
    public Collection<PersonInfoForFlood> getPersonnsByIdStation(String[] idStationList) {
        int nbMineurs = 0;
        int nbMajeurs = 0;
        Collection<PersonInfoForFlood> personInfoCollection = new ArrayList<>();

        for (String idStation : idStationList) {

            // Pour chaque adresse correspondant à stationNumber
            for (String address : dataRepository.getAddressByIdStation(idStation)) {
                // On cherche les personnes correspondantes
                for (Person person : dataRepository.getPersonByAddress(address)) {
                    PersonInfoForFlood personInfoForFlood = new PersonInfoForFlood();
                    personInfoForFlood.setLastName(person.getLastName());
                    personInfoForFlood.setFirstName(person.getFirstName());
                    personInfoForFlood.setPhone(person.getPhone());

                    Medicalrecord medicalrecord = dataRepository.getMedicalrecordByName(person.getLastName(), person.getFirstName());
                    int age = dataRepository.calculateAge(medicalrecord.getBirthdate());

                    personInfoForFlood.setAge(age);

                    if (age <= 18) {
                        nbMineurs++;
                    } else nbMajeurs++;

                    personInfoForFlood.setMedications(medicalrecord.getMedications());
                    personInfoForFlood.setAllergies(medicalrecord.getAllergies());

                    personInfoCollection.add(personInfoForFlood);
                }
            }
        }
        return  personInfoCollection;
    }

    @Override
    public List<Person> getListPersons() {
        return dataRepository.database.getPersons();
    }
}


