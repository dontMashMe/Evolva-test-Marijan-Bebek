-- Set of scripts for setting up a test database environment.
-- Also to be used for initial production database setup.

-- Create Currency table
CREATE TABLE Currency (
    ID SERIAL PRIMARY KEY,
    CurrencyName VARCHAR NOT NULL
);

-- Create Country table
CREATE TABLE Country (
    ID SERIAL PRIMARY KEY,
    CountryName VARCHAR NOT NULL
);

-- Create City table
CREATE TABLE City (
    ID SERIAL PRIMARY KEY,
    CityName VARCHAR NOT NULL,
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

-- City table insert procedure.
CREATE OR REPLACE PROCEDURE insert_city(cityName VARCHAR, countryNameP VARCHAR)
LANGUAGE plpgsql
AS $$
DECLARE
    countryId INT;
BEGIN
    SELECT id INTO countryId FROM Country WHERE countryName = countryNameP;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Country not found: %', countryName;
    END IF;

    INSERT INTO City (cityName, countryId) VALUES (cityName, countryId);
END;
$$;

-- Trip table insert procedure.
CREATE OR REPLACE PROCEDURE insert_trip(currencyNameP VARCHAR, cityNameP VARCHAR, savedAmount INT)
LANGUAGE plpgsql
AS $$
DECLARE
    currencyId INT;
    cityId INT;
BEGIN
    -- Retrieve the currency ID based on the currency name
    SELECT id INTO currencyId FROM Currency WHERE currencyName = currencyNameP;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Currency not found: %', currencyName;
    END IF;

    -- Retrieve the city ID based on the city name
    SELECT id INTO cityId FROM City WHERE cityName = cityNameP;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'City not found: %', cityName;
    END IF;

    -- Insert the new trip into the Trip table
    INSERT INTO Trip (currencyId, cityId, savedAmount) VALUES (currencyId, cityId, savedAmount);
END;
$$;

-- DO NOT RUN THESE WHEN INITIALLY SETTING UP THE PROD DATABASE.
-- Prod database data should come via CSV files!

-- Insert dummy data into Currency table
INSERT INTO Currency (CurrencyName) VALUES ('USD');
INSERT INTO Currency (CurrencyName) VALUES ('EUR');
INSERT INTO Currency (CurrencyName) VALUES ('GBP');

-- Insert dummy data into Country table
INSERT INTO Country (CountryName) VALUES ('United States');
INSERT INTO Country (CountryName) VALUES ('Germany');
INSERT INTO Country (CountryName) VALUES ('United Kingdom');

-- Insert dummy data into City table
INSERT INTO City (CityName, CountryID) VALUES ('New York', 1);
INSERT INTO City (CityName, CountryID) VALUES ('Berlin', 2);
INSERT INTO City (CityName, CountryID) VALUES ('London', 3);

-- Insert dummy data into Trip table
INSERT INTO Trip (CurrencyID, CityID, SavedAmount) VALUES (1, 1, 1000);
INSERT INTO Trip (CurrencyID, CityID, SavedAmount) VALUES (2, 2, 2000);
INSERT INTO Trip (CurrencyID, CityID, SavedAmount) VALUES (3, 3, 3000);
