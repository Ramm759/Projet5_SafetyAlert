package com.mycompany.safetyAlert.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.safetyAlert.exceptions.DataRepositoryException;
import com.mycompany.safetyAlert.model.Database;
import com.mycompany.safetyAlert.model.Firestation;
import com.mycompany.safetyAlert.model.Medicalrecord;
import com.mycompany.safetyAlert.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Repository
public class DataRepository {
    // Mappage Json vers objet Java
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Log4j
    private static final Logger logger = LogManager.getLogger(DataRepository.class);

    // chargement du fichier Json en mémoire
    public static Database database;
    private String DATA_JSON = "data.json";

    // On met à jour uniquement si commit = true (pas pour les tests)
    private boolean commit = true;

    public DataRepository() throws IOException {
        this.init();
    }

    public void init() {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(DATA_JSON)) {
            database = objectMapper.readerFor(Database.class).readValue(inputStream);
            logger.info("OK - File open : " + DATA_JSON);
        } catch (FileNotFoundException fnfe) {
            logger.info("KO - File not found " + DATA_JSON);
            throw new DataRepositoryException("KO - File not found", fnfe);
        } catch (IOException ioe) {
            logger.info("KO - IO error " + DATA_JSON);
            throw new DataRepositoryException("KO - IO error", ioe);
        }
    }

    public void commit() {
        if (commit) {
            URL url = ClassLoader.getSystemResource(DATA_JSON);
            // ouverture d'un flux d'écriture
            try (OutputStream outputStream = new FileOutputStream(url.getFile())) {
                // ecriture avec un formatage (retour ligne)
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, database);
                logger.info("OK - File update : " + DATA_JSON);
            } catch (FileNotFoundException fnfe) {
                logger.info("KO - File not found " + DATA_JSON);
                throw new DataRepositoryException("KO - File not found", fnfe);
            } catch (IOException ioe) {
                logger.info("KO - IO error " + DATA_JSON);
                throw new DataRepositoryException("KO - IO error", ioe);
            }
        }
    }

    public void setCommit(boolean commit) {
        this.commit = commit;
    }

    /**
     * @param city
     * @return Collection de Person habitant dans city
     */
    public Collection<Person> getPersonByCity(String city) {
        Collection<Person> personCollection = new ArrayList<Person>();
        for (Person person : database.getPersons()) {
            if (person.getCity().equalsIgnoreCase(city)) {
                personCollection.add(person);
            }
        }
        return personCollection;
    }

    /**
     * @param address
     * @return Collection de Person habitant à address
     */
    public Collection<Person> getPersonByAddress(String address) {
        Collection<Person> personCollection = new ArrayList<>();
        for (Person person : database.getPersons()) {
            if (person.getAddress().equalsIgnoreCase(address)) {
                personCollection.add(person);
            }
        }
        return personCollection;
    }

    public int getStationNumber(String address) {
        int stationNumber = 0;
        for (Firestation firestation : database.getFirestations()) {
            if (firestation.getAddress().equals(address)) {
                stationNumber = firestation.getStation();
            }
        }
        return stationNumber;
    }

    /**
     * @param idStation
     * @return collection d'adresses dépendant de idStation
     */
    public Collection<String> getAddressByIdStation(int idStation) {
        Collection<String> addressCollection = new ArrayList<>();
        for (Firestation firestation : database.getFirestations()) {
            if (firestation.getStation() == idStation) {
                addressCollection.add(firestation.getAddress());
            }
        }
        return addressCollection;
    }

    public List<Person> listPersonnByName(String lastName, String firstName) {
        List<Person> personList = new ArrayList<>();
        Database db = DataRepository.database;
        for (Person person : db.getPersons()) {
            if ((lastName == null
                    || (person.getLastName().equalsIgnoreCase(lastName)))
                    && (firstName == null || person.getFirstName()
                    .equalsIgnoreCase(firstName))) {
                personList.add(person);
            }
        }
        return personList;
    }

    public Medicalrecord getMedicalrecordByName(String lastName, String firstName) {
        Medicalrecord medicalrecordInfo = new Medicalrecord();
        Database db = DataRepository.database;
        for (Medicalrecord medicalrecord : db.getMedicalrecords()) {
            if ((medicalrecord.getLastName().equalsIgnoreCase(lastName))
                    && (medicalrecord.getFirstName()
                    .equalsIgnoreCase(firstName))) {
                medicalrecordInfo = medicalrecord;
            }
        }
        return medicalrecordInfo;
    }

    public int calculateAge(String dateNaissance) {
        LocalDate actualDate = LocalDate.now();
        LocalDate birthDate = null;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.FRANCE);
            birthDate = LocalDate.parse(dateNaissance.toLowerCase(), formatter);
        } catch (DateTimeParseException exc) {
            System.out.printf("%s is not parsable!%n", dateNaissance);
            throw exc;
        }

        int age = (Period.between(birthDate, actualDate)).getYears();
        return age;
    }
}
