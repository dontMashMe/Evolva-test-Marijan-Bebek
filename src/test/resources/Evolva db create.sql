-- Create Currency table
CREATE TABLE Currency (
    ID SERIAL PRIMARY KEY,
    CurrencyName CHAR(50) NOT NULL
);

-- Create Country table
CREATE TABLE Country (
    ID SERIAL PRIMARY KEY,
    CountryName CHAR(50) NOT NULL
);

-- Create City table
CREATE TABLE City (
    ID SERIAL PRIMARY KEY,
    CityName CHAR(50) NOT NULL,
    CountryID INT NOT NULL,
    CONSTRAINT fk_country FOREIGN KEY (CountryID) REFERENCES Country(ID)
);

-- Create Trip table
CREATE TABLE Trip (
    ID SERIAL PRIMARY KEY,
    CurrencyID INT NOT NULL,
    CityID INT NOT NULL,
    SavedAmount INT NOT NULL,
    CONSTRAINT fk_currency FOREIGN KEY (CurrencyID) REFERENCES Currency(ID),
    CONSTRAINT fk_city FOREIGN KEY (CityID) REFERENCES City(ID)
);
