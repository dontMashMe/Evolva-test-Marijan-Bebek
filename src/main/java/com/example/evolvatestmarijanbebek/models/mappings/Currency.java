package com.example.evolvatestmarijanbebek.models.mappings;

public class Currency {
    private Long id;
    private String CurrencyName;


    public Currency(Long id, String currencyName) {
        this.id = id;
        CurrencyName = currencyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public void setCurrencyName(String currencyName) {
        CurrencyName = currencyName;
    }
}
