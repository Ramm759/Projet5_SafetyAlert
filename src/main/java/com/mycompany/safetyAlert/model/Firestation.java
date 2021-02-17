package com.mycompany.safetyAlert.model;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Firestation {
    @NotBlank
    private String address;
    @NotBlank
    private String station;

    public Firestation() {
    }

    public Firestation(@NotBlank String address, @NotBlank String station) {
        this.address = address;
        this.station = station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Firestation that = (Firestation) o;
        return address.equals(that.address) && station.equals(that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
