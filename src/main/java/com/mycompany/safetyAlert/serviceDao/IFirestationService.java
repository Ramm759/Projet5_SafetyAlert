package com.mycompany.safetyAlert.serviceDao;

import com.mycompany.safetyAlert.model.Firestation;
import com.mycompany.safetyAlert.model.Person;

public interface IFirestationService {
    boolean createFirestation (Firestation firestation);
    boolean deleteFirestation (Firestation firestation);
    boolean updateFirestation (Firestation firestation);
}
