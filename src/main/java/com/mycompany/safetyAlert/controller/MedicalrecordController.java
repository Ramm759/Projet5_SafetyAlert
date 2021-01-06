package com.mycompany.safetyAlert.controller;

import com.mycompany.safetyAlert.dao.FirestationDao;
import com.mycompany.safetyAlert.dao.MedicalrecordDao;
import com.mycompany.safetyAlert.model.Firestation;
import com.mycompany.safetyAlert.model.Medicalrecord;
import com.mycompany.safetyAlert.serviceDao.IFirestationService;
import com.mycompany.safetyAlert.serviceDao.IMedicalrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MedicalrecordController {

    @Autowired
    private IMedicalrecordService medicalrecordService;

    @Autowired
    private MedicalrecordDao medicalrecordDao;

    @PostMapping(path = "medicalrecord")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMedicalrecord(@RequestBody @Valid Medicalrecord medicalrecord) {
        medicalrecordDao.createMedicalrecord(medicalrecord);
    }

    @PutMapping(path = "medicalrecord")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMedicalrecord(@RequestBody @Valid Medicalrecord medicalrecord) {
        medicalrecordDao.updateMedicalrecord(medicalrecord);
    }

    @DeleteMapping(path = "medicalrecord")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteMedicalrecord(@RequestBody @Valid Medicalrecord medicalrecord) {
        medicalrecordDao.deleteMedicalrecord(medicalrecord);
    }
}

