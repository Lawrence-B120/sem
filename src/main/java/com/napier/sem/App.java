package com.napier.sem;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
    private enum AreaType {
        World,
        Continent,
        Region,
        Country,
        District
    }

    private Connection con = null;

    public static void main(String[] args) {
        //Enum for functions requiring an area type
        AreaType areaType;

        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Display Example Country
        a.displayCountry(a.getCountry("ABW"));

        //display example fo city report
        a.displayCityReport(a.getCity("JPN"));

        //display example capital city report
        a.displayCapitalCityReport(a.getCapitalCity("GBR"));
        a.displayCityReport(a.getCity("JPN"));
        //Display country population report
        //areaType = AreaType.Continent;
        //a.displayPopulation(a.getPopulation(areaType));

        // Disconnect from database
        a.disconnect();
    }

    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    //Get the details of a country from the database
    public Country getCountry(String ID) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Code, country.Name, Continent, Region, city.Name, country.Population "
                            + "FROM country JOIN city "
                            + "ON country.Capital=city.ID "
                            + "WHERE Code = '" + ID + "'";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next()) {
                Country cnt = new Country();
                cnt.code = rset.getString("Code");
                cnt.name = rset.getString("country.Name");
                cnt.continent = rset.getString("Continent");
                cnt.region = rset.getString("Region");
                cnt.capital = rset.getString("city.Name");
                cnt.population = rset.getInt("country.Population");
                return cnt;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    //Get Capital City from Database
    public CapitalCity getCapitalCity(String ID) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, CountryCode, District, city.Population, country.Capital, country.Name "
                            + "FROM city JOIN country "
                            + "ON country.Capital=city.ID "
                            + "WHERE Code = '" + ID + "'";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next()) {
                CapitalCity cptc = new CapitalCity();
                //cptc.id = rset.getString("ID");
                cptc.name = rset.getString("city.Name");
                cptc.country = rset.getString("country.Name");
                cptc.district = rset.getString("District");
                cptc.population = rset.getInt("city.Population");
                return cptc;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    public CityReport getCity(String ID) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, CountryCode, District, city.Population, country.Capital, country.Name "
                            + "FROM city JOIN country "
                            + "ON country.Capital=city.ID "
                            + "WHERE Code = '" + ID + "'";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next()) {
                CityReport city = new CityReport();
                //cptc.id = rset.getString("ID");
                city.name = rset.getString("city.Name");
                city.country = rset.getString("country.Name");
                city.district = rset.getString("District");
                city.population = rset.getInt("city.Population");
                return city;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    //Get the details of a country from the database
    public List<Population> getPopulation(AreaType areaType)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect = "";
            if (areaType == AreaType.Country)
            {
                strSelect =
                        "SELECT co.Name AS Name, SUM(DISTINCT co.Population) AS TotalPop, "
                                + "SUM(ci.Population) AS CityPop, (SUM(ci.Population)/SUM(DISTINCT co.Population))*100 AS CityPercent, "
                                + "SUM(DISTINCT co.Population) - SUM(ci.Population) AS NonCityPop, ((SUM(DISTINCT co.Population) - SUM(ci.Population))/SUM(DISTINCT co.Population))*100 AS NonCityPercent "
                                + "FROM country co JOIN city ci ON co.Code = ci.CountryCode "
                                + "GROUP BY `Name` "
                                + "ORDER BY `Name` ";
            }
            else
            {
                strSelect =
                        "SELECT co." + areaType.toString() + " AS Name, SUM(DISTINCT co.Population) AS TotalPop, "
                                + "SUM(ci.Population) AS CityPop, (SUM(ci.Population)/SUM(DISTINCT co.Population))*100 AS CityPercent, "
                                + "SUM(DISTINCT co.Population) - SUM(ci.Population) AS NonCityPop, ((SUM(DISTINCT co.Population) - SUM(ci.Population))/SUM(DISTINCT co.Population))*100 AS NonCityPercent "
                                + "FROM country co JOIN city ci ON co.Code = ci.CountryCode "
                                + "GROUP BY `Name` "
                                + "ORDER BY `Name` ";
            }
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            List<Population> popList = new LinkedList<Population>();
            if (rset.next())
            {
                do {
                    Population pop = new Population();
                    pop.name = rset.getString("Name");
                    pop.totalPop = rset.getString("TotalPop");
                    pop.cityPop = rset.getString("CityPop");
                    pop.nonCityPop = rset.getString("NonCityPop");
                    pop.cityPercent = rset.getFloat("CityPercent");
                    pop.nonCityPercent = rset.getFloat("NonCityPercent");
                    popList.add(pop);
                }
                while (rset.next());
                return popList;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    //Displays details of a given Country object to terminal
    public void displayCountry(Country cnt)
    {
        if (cnt != null)
        {
            System.out.println(
                    cnt.code + " "
                            + cnt.name + "\n"
                            + "Continent: " + cnt.continent + "\n"
                            + "Region: " + cnt.region + "\n"
                            + "Population: " + cnt.population + "\n"
                            + "Capital: " + cnt.capital + "\n");
        }
    }

    //Displays details of a given CapitalCity object to terminal
    public void displayCapitalCityReport(CapitalCity cptc)
    {
        if(cptc != null)
        {
            System.out.println(
                    //cptc.code + " "
                            "Capital City " + cptc.name + "\n"
                            + "Country " + cptc.country + "\n"
                            +  "Capital Population " + cptc.population + "\n"
            );
        }
    }

    public void displayPopulation(List<Population> popList)
    {
        if (popList != null)
        {
            StringBuilder printString = new StringBuilder();
            for (Population pop : popList) {
                printString.append(pop.name).append("\n")
                        .append("Total Population: ").append(pop.totalPop).append("\n")
                        .append("City Population: ").append(pop.cityPop).append(" (").append(pop.cityPercent).append("%)").append("\n")
                        .append("Non-City Population: ").append(pop.nonCityPop).append(" (").append(pop.nonCityPercent).append("%)").append("\n\n");
            }
            System.out.println(printString);
        }
    }

    //Displays details of a given CapitalCity object to terminal
    public void displayCityReport(CityReport city)
    {
        if(city != null)
        {
            System.out.println(
                    //cptc.code + " "
                    "City " + city.name + "\n"
                            + "Country " + city.country + "\n"
                            + "District " + city.district + "\n"
                            +  "Capital Population " + city.population + "\n"
            );
        }
    }
}