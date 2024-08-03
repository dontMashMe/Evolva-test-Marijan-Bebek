package com.example.evolvatestmarijanbebek.models.mappings;

import java.util.Objects;

public class Country {
    private Long id;
    private String CountryName;

    public Country(Long id, String countryName) {
        this.id = id;
        this.CountryName = countryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", CountryName='" + CountryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) && Objects.equals(CountryName, country.CountryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, CountryName);
    }

}
