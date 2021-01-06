package com.mycompany.safetyAlert.controller;

import com.mycompany.safetyAlert.dao.FirestationDao;
import com.mycompany.safetyAlert.model.Firestation;
import com.mycompany.safetyAlert.serviceDao.IFirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class FirestationController {

    @Autowired
    private IFirestationService firestationService;

    @Autowired
    private FirestationDao firestationDao;

    @PostMapping(path = "firestation")
    @ResponseStatus(HttpStatus.CREATED)
    public void createFirestation(@RequestBody @Valid Firestation firestation) {
        firestationDao.createFirestation(firestation);
    }

    @PutMapping(path = "firestation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFirestation(@RequestBody @Valid Firestation firestation) {
        firestationDao.updateFirestation(firestation);
    }

    @DeleteMapping(path = "firestation")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteFirestation(@RequestBody @Valid Firestation firestation) {
        firestationDao.deleteFirestation(firestation);
    }
}

