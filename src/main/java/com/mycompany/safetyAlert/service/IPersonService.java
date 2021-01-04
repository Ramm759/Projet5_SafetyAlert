package com.mycompany.safetyAlert.service;

import com.mycompany.safetyAlert.dto.PersonInfo;
import com.mycompany.safetyAlert.dto.PersonInfoWithFirestationNb;
import com.mycompany.safetyAlert.dto.PersonInfoWithMinor;

import java.util.ArrayList;
import java.util.Collection;

public interface IPersonService {
    Collection<PersonInfoWithMinor> getPersonInfo(int stationNumber);
    Collection<PersonInfo> getChildrenByAddress(String address);
    Collection<String> getCommunityEmail(String city);
    Collection<String> getCommunityPhone(int stationNumber);
    Collection<PersonInfo> getPersonInfo(String lastName, String firstName);
    Collection<PersonInfoWithFirestationNb> getPersonnsByAdress (String address);
    Collection<PersonInfo> getPersonnsByIdStation(int[] idStationList);
}
