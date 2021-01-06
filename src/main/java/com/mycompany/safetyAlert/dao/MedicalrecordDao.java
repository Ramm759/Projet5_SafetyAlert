package com.mycompany.safetyAlert.dao;

import com.mycompany.safetyAlert.model.Medicalrecord;

public interface MedicalrecordDao {
    boolean createMedicalrecord (Medicalrecord medicalrecord);
    boolean deleteMedicalrecord (Medicalrecord medicalrecord);
    boolean updateMedicalrecord (Medicalrecord medicalrecord);
}
