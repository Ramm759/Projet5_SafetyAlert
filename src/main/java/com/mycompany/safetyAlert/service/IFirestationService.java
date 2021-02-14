package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.model.Firestation;

public interface IFirestationService {
    boolean createFirestation (Firestation firestation);
    boolean deleteFirestation (Firestation firestation);
    boolean updateFirestation (Firestation firestation);
}
