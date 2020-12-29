package com.mycompany.safetyAlert.model;

import java.util.Objects;

public class Firestation {
    private String address;
    private int station;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Firestation)) return false;
        Firestation that = (Firestation) o;
        return Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }
}
