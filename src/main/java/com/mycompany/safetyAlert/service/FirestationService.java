package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.exceptions.DataAlreadyExistException;
import com.mycompany.safetyAlert.exceptions.DataNotFoundException;
import com.mycompany.safetyAlert.model.Firestation;
import com.mycompany.safetyAlert.repository.DataRepository;
import com.mycompany.safetyAlert.serviceDao.IFirestationServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirestationService implements IFirestationService {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    IFirestationServiceDao firestationService;

    @Override
    public boolean createFirestation(Firestation firestation) {
        // On vérifie que la caserne n'existe pas dans la Dao)
        if (!dataRepository.database.getFirestations().contains(firestation)) {
            firestationService.createFirestation(firestation);
            return true;
        } else {
            throw new DataAlreadyExistException("La caserne numéro " + firestation.getStation() + " à l'adresse " + firestation.getAddress() + " existe déjà.");
        }
    }

    @Override
    public boolean deleteFirestation(Firestation firestation) {
        if (!firestationService.deleteFirestation(firestation)){
            throw new DataNotFoundException("La caserne numéro " + firestation.getStation() + " à l'adresse " + firestation.getAddress() +  " n'existe pas.");
        }
        return true;
    }

    @Override
    public boolean updateFirestation(Firestation firestation) {
        if (!firestationService.updateFirestation(firestation)) {
            throw new DataNotFoundException("La caserne numéro " + firestation.getStation() + " à l'adresse " + firestation.getAddress() +  " n'existe pas.");
        }
        return true;
    }
}
