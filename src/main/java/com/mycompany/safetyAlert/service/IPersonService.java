package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.dto.*;

import java.util.Collection;

public interface IPersonService {
    Collection<PersonInfoWithMinor> getPersonInfo(int stationNumber);
    Collection<PersonInfoForChildAlert> getChildrenByAddress(String address);
    Collection<String> getCommunityEmail(String city);
    Collection<String> getCommunityPhone(int stationNumber);
    Collection<PersonInfoWithoutPhone> getPersonInfo(String lastName, String firstName);
    Collection<PersonInfoWithFirestationNb> getPersonnsByAdress (String address);
    Collection<PersonInfoForFlood> getPersonnsByIdStation(int[] idStationList);
}
