package com.mycompany.safetyAlert.dao;

import com.mycompany.safetyAlert.model.Firestation;

public interface FirestationDao {
    boolean createFirestation (Firestation firestation);
    boolean deleteFirestation (Firestation firestation);
    boolean updateFirestation (Firestation firestation);
}
