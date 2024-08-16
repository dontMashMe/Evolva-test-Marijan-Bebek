# SantaTrips

Due to rising inflation, high CoL, stagnant wages and ever-tighter strain on his budget, Santa decided to track how much money he saved on each of his trips around the world. 

His hardworking elves are tasked with creating a report for each country visited, in the following format: *country.csv*. 

Content of the report will be:

**city**,**currency**,**amount_saved**.

SantaTrips is a hastily constructed solution by one of his elves, which aims to aggregate the trip data into total amount saved in each country, by currency.

## Problem Statement
The task is to parse the CSV files provided by Santa’s elves, aggregate the savings data by currency for each country, and generate a summary report.

## Example input
(assume the Input directory is (root/**uploads**)

* **croatia.csv**
    * Zagreb, EUR, 1
    * Osijek, EUR, 2
    * Rijeka, EUR, 4
    * Rijeka, HRK, 30
    * Zagreb, HRK, 10
    * Split, USD, 70
* **germany.csv**
    * Berlin, EUR, 10
    * Muenchen, EUR, 20
* **usa.csv**
    * New York, USD, 300
    * Los Angeles, USD, 400

## Example output
For each file processed, the program should output the total amount saved by currency.

```
"croatia.csv" found
     Totals by currencies:
         HRK: 40
         EUR: 7
         USD: 70
"germany.csv" found
     Totals by currencies:
         EUR: 30
"usa.csv" found
     Totals by currencies:
         USD: 700
```

## Prerequisites
* Java 11 or higher
* PostgreSQL 13 or higher
* Maven 3.6.3 or higher

## Setup
Mainly seting up the database which the app needs.

#### Create pg database
Requires PostgreSQL database to run. Database name should be **postgres** (this is hardcoded, no intention of changing it right now).

#### Create db.properties
In the `src/main/resources/com/example/evolvatestmarijanbebek/` directory, create a file *db.properties*.
Format should be: 
```
# db.properties
user=postgres
password=postgres
```

#### Run the init.sql
Run the scripts (in the order which they are written in) from file: [init.sql](https://github.com/dontMashMe/Evolva-test-Marijan-Bebek/blob/master/src/test/resources/init.sql)

Note:
If you're using the `psql -U username -d myDataBase -a -f myInsertFile` command, make sure to comment out the dummy inserts starting from line **81** (or just create a different copy of the file without the inserts)

#### Creating the test database
If you wish to run the unit & integration tests, you need to create a new database **testdb** (use the same user/password as for the **postgres**). 

No need to run the init.sql script, the tests themselves run it.

## Installation
Assuming you've done the database setup from the previous chapter.

1. Clone the repository:
    ```
    git clone https://github.com/dontMashMe/Evolva-test-Marijan-Bebek.git
    cd Evolva-test-Marijan-Bebek
    ```

2. Build the project using Maven:
    ```
    mvn clean install
    ```

3. Run the application:
    ```
    java -jar target/santatrips.jar
    ```

