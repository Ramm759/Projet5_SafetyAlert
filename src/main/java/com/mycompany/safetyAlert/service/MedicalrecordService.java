package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.exceptions.DataAlreadyExistException;
import com.mycompany.safetyAlert.exceptions.DataNotFoundException;
import com.mycompany.safetyAlert.model.Medicalrecord;
import com.mycompany.safetyAlert.model.Person;
import com.mycompany.safetyAlert.repository.DataRepository;
import com.mycompany.safetyAlert.serviceDao.IMedicalrecordServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalrecordService implements IMedicalrecordService {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    IMedicalrecordServiceDao medicalrecordService;

    @Override
    public boolean createMedicalrecord(Medicalrecord medicalrecord) {
        // on vérifie que la personne existe
        Person person = new Person();
        person.setLastName(medicalrecord.getLastName());
        person.setFirstName(medicalrecord.getFirstName());

        if (!dataRepository.getAllPersons().contains(person)){
            throw new DataNotFoundException("La personne " + person.getLastName() +" " + person.getFirstName() + " n'existe pas");
        }

        else {
        // On vérifie que l'enregistrement n'existe pas dans la Dao
        if ((!dataRepository.getAllMedicalRecord().contains(medicalrecord))) {
            medicalrecordService.createMedicalrecord(medicalrecord);
            return true;
        } else {
            throw new DataAlreadyExistException("L'enregistrement pour " + medicalrecord.getLastName() + " " + medicalrecord.getFirstName() + " existe déjà.");
        }
    }}

    @Override
    public boolean deleteMedicalrecord(Medicalrecord medicalrecord) {
        if (!medicalrecordService.deleteMedicalrecord(medicalrecord)){
            throw new DataNotFoundException("L'enregistrement pour " + medicalrecord.getLastName() + " " + medicalrecord.getFirstName() + " n'existe pas.");
        }
        return true;
    }

    @Override
    public boolean updateMedicalrecord(Medicalrecord medicalrecord) {
        if (!medicalrecordService.updateMedicalrecord(medicalrecord)) {
            throw new DataNotFoundException("L'enregistrement pour " + medicalrecord.getLastName() + " " + medicalrecord.getFirstName() +  " n'existe pas.");
        }
        return true;
    }
}
