package com.mycompany.safetyAlert.controller;

import com.mycompany.safetyAlert.dao.PersonDao;
import com.mycompany.safetyAlert.dto.*;
import com.mycompany.safetyAlert.model.Person;
import com.mycompany.safetyAlert.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

// On expose les Api Rest pour gérer les requêtes qui viennent d'un client web
@RestController
public class PersonController {
    @Autowired
    private IPersonService personService;
    @Autowired
    private PersonDao personDao;

    @PostMapping(path = "person")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@RequestBody @Valid Person person){
      personDao.createPerson(person);
    }

    @PutMapping(path = "person")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePerson(@RequestBody @Valid Person person){
        personDao.updatePerson(person);
    }

    @DeleteMapping(path = "person")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deletePerson(@RequestBody @Valid Person person){
        personDao.deletePerson(person);
    }

    @GetMapping(path = "firestation")
    public Collection<PersonInfoWithMinor> getPersonsBystationNumber(@RequestParam int stationNumber) {
        return personService.getPersonInfo(stationNumber);
    }

    @GetMapping(path = "childAlert")
    public Collection<PersonInfoForChildAlert> getChildrenByAddress(@RequestParam String address) {
        return personService.getChildrenByAddress(address);
    }

    @GetMapping(path = "phoneAlert")
    public Collection<String> getCommunityPhone(@RequestParam int firestation) {
        return personService.getCommunityPhone(firestation);
    }

    @GetMapping(path = "fire")
    public Collection<PersonInfoWithFirestationNb> getCommunityByAddress(@RequestParam String address) {
        return personService.getPersonnsByAdress(address);
    }

    @GetMapping(path = "flood/stations")
    public Collection<PersonInfoForFlood> getCommunityByIdStation(@RequestParam int[] stations){
        return personService.getPersonnsByIdStation(stations);
    }

    @GetMapping(path = "personInfo")
    public Collection<PersonInfoWithoutPhone> getPersonInfos(@RequestParam String lastName, @RequestParam(required = false) String firstName) {
        return personService.getPersonInfo(lastName, firstName);
    }
    @GetMapping(path = "communityEmail")
    public Collection<String> getCommunityEmail(@RequestParam String city) {
        return personService.getCommunityEmail(city);
    }
}

