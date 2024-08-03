package com.example.evolvatestmarijanbebek.models.mappings;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(id, currency.id) && Objects.equals(CurrencyName, currency.CurrencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, CurrencyName);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", CurrencyName='" + CurrencyName + '\'' +
                '}';
    }
}
