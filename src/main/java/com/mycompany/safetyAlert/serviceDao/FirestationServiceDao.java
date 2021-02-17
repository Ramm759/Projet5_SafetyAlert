package com.mycompany.safetyAlert.serviceDao;

import com.mycompany.safetyAlert.model.Firestation;
import com.mycompany.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirestationServiceDao implements IFirestationServiceDao {
    @Autowired
    DataRepository dataRepository;

    @Override
    public boolean createFirestation(Firestation firestation) {
        dataRepository.getAllFirestations().add(firestation); // ajout en mémoire de la nouvelle caserne
        dataRepository.commit(); // écriture dans le fichier Json
        return true;
    }

    @Override
    public boolean deleteFirestation(Firestation firestation) {
        boolean result = dataRepository.getAllFirestations().remove(firestation);
        dataRepository.commit();
        return result;
    }

    @Override
    public boolean updateFirestation(Firestation firestation) {
        if (dataRepository.getAllFirestations().remove(firestation)) {
            createFirestation(firestation);
            return true;
        }
        return false;
    }
}
