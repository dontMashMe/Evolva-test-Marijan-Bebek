package com.example.evolvatestmarijanbebek.models;

import java.util.Objects;

public class Trip {
    private Long id;
    private String currencyName;
    private String cityName;
    private Integer savedAmount;

    public Trip(Long id, String currencyName, String cityName, Integer savedAmount) {
        this.id = id;
        this.currencyName = currencyName;
        this.cityName = cityName;
        this.savedAmount = savedAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(Integer savedAmount) {
        this.savedAmount = savedAmount;
    }


    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /*
    * Needed for test purposes.*/
    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", currencyName='" + currencyName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", savedAmount=" + savedAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id) && Objects.equals(currencyName, trip.currencyName) && Objects.equals(cityName, trip.cityName) && Objects.equals(savedAmount, trip.savedAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyName, cityName, savedAmount);
    }
}
