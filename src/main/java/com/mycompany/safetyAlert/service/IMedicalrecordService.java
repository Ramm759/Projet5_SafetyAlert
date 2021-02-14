package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.model.Medicalrecord;

public interface IMedicalrecordService {
    boolean createMedicalrecord (Medicalrecord medicalrecord);
    boolean deleteMedicalrecord (Medicalrecord medicalrecord);
    boolean updateMedicalrecord (Medicalrecord medicalrecord);
}
