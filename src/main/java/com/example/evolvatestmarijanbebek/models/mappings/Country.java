package com.example.evolvatestmarijanbebek.models.mappings;

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
}
