package com.mycompany.safetyAlert.serviceDao;

import com.mycompany.safetyAlert.model.Medicalrecord;
import com.mycompany.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalrecordServiceDao implements IMedicalrecordServiceDao {
    @Autowired
    DataRepository dataRepository;

    @Override
    public boolean createMedicalrecord(Medicalrecord medicalrecord) {
        dataRepository.getAllMedicalRecord().add(medicalrecord); // ajout en mémoire
        dataRepository.commit(); // écriture dans le fichier Json
        return true;
    }

    @Override
    public boolean deleteMedicalrecord(Medicalrecord medicalrecord) {
        boolean result = dataRepository.getAllMedicalRecord().remove(medicalrecord);
        dataRepository.commit();
        return result;
    }

    @Override
    public boolean updateMedicalrecord(Medicalrecord medicalrecord) {
        if (dataRepository.getAllMedicalRecord().remove(medicalrecord)) {
            createMedicalrecord(medicalrecord);
            return true;
        }
        return false;
    }
}
