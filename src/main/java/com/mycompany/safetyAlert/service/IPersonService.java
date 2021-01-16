package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.dto.*;
import com.mycompany.safetyAlert.model.Person;

import java.util.Collection;
import java.util.List;

public interface IPersonService {
    Collection<PersonInfoWithMinor> getPersonInfo(int stationNumber);
    Collection<PersonInfoForChildAlert> getChildrenByAddress(String address);
    Collection<String> getCommunityEmail(String city);
    Collection<String> getCommunityPhone(int stationNumber);
    Collection<PersonInfoWithoutPhone> getPersonInfo(String lastName, String firstName);
    Collection<PersonInfoWithFirestationNb> getPersonnsByAdress (String address);
    Collection<PersonInfoForFlood> getPersonnsByIdStation(int[] idStationList);
    List<Person> getListPersons();
}
