package com.mycompany.safetyAlert.serviceUtils;

import com.mycompany.safetyAlert.dto.*;
import com.mycompany.safetyAlert.model.Person;

import java.util.Collection;
import java.util.List;

public interface IPersonUtils {
    Collection<PersonInfoWithMinor> getPersonInfo(String stationNumber);
    Collection<PersonInfoForChildAlert> getChildrenByAddress(String address);
    Collection<String> getCommunityEmail(String city);
    Collection<String> getCommunityPhone(String stationNumber);
    Collection<PersonInfoWithoutPhone> getPersonInfo(String lastName, String firstName);
    Collection<PersonInfoWithFirestationNb> getPersonnsByAdress (String address);
    Collection<PersonInfoForFlood> getPersonnsByIdStation(String[] idStationList);
    List<Person> getListPersons();
}
