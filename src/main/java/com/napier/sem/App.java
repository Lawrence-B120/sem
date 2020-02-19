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
        District,
        City
    }

    private Connection con = null;

    public static void main(String[] args) {
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

        //Display country population report
        //a.displayPopulation(a.getPopulation(areaType));

        //Display language report
        a.displayLanguage(a.getLanguage());

        //Display Population fo world
        System.out.println("_Simple Populations_\n");
        a.displayPopWorld(a.getPopulations(AreaType.World,""), "");
        a.displayPopWorld(a.getPopulations(AreaType.Continent,"Asia"), "Asia");
        a.displayPopWorld(a.getPopulations(AreaType.Region, "Australia and New Zealand"), "Australia and New Zealand");
        a.displayPopWorld(a.getPopulations(AreaType.Country, "France"), "France");
        a.displayPopWorld(a.getPopulations(AreaType.District, "Chaco"), "Chaco");
        a.displayPopWorld(a.getPopulations(AreaType.City, "Edinburgh"), "Edinburgh");
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
                System.out.println("Successfully connected\n");
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
                Country cnt = new Country(rset.getString("Code"),
                        rset.getString("country.Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getString("city.Name"),
                        rset.getInt("country.Population"));
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
                CapitalCity cptc = new CapitalCity(rset.getString("city.Name"),
                        rset.getString("country.Name"),
                        rset.getString("District"),
                        rset.getInt("city.Population"),
                        rset.getInt("ID"));
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
                CityReport city = new CityReport(rset.getString("city.Name"),
                        rset.getString("country.Name"),
                        rset.getString("District"),
                        rset.getInt("city.Population"));
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
                    Population pop = new Population(rset.getString("Name"),
                            rset.getString("TotalPop"),
                            rset.getString("CityPop"),
                            rset.getString("NonCityPop"),
                            rset.getFloat("CityPercent"),
                            rset.getFloat("NonCityPercent"));
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
            System.out.println("Failed to get population details");
            return null;
        }
    }

    public List<Language> getLanguage() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT countrylanguage.Language AS Name, "
                            + "SUM((country.Population * countrylanguage.Percentage)/100) AS Population, "
                            + "(SUM((country.Population * countrylanguage.Percentage)/100)/(SELECT SUM(Population) FROM country))*100 AS Percentage "
                            + "FROM countrylanguage JOIN country "
                            + "ON countrylanguage.CountryCode=country.Code "
                            + "WHERE countrylanguage.Language = 'Chinese' OR countrylanguage.Language = 'English' OR countrylanguage.Language = 'Hindi' OR countrylanguage.Language = 'Spanish' OR countrylanguage.Language = 'Arabic' "
                            + "GROUP BY countrylanguage.Language "
                            + "ORDER BY Population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            List<Language> langList = new LinkedList<Language>();
            if (rset.next())
            {
                do {
                    Language lang = new Language(rset.getString("Name"),
                            rset.getInt("Population"),
                            rset.getFloat("Percentage"));
                    langList.add(lang);
                }
                while (rset.next());
                return langList;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get language details");
            return null;
        }
    }

    public PopulationCategories getPopulations(AreaType areaType, String name)
    {
        PopulationCategories popc = new PopulationCategories();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String stringSelect = "";
            if(areaType == AreaType.World)
            {
                stringSelect =
                        "SELECT SUM(Population) as pop FROM country ";
                popc.SetCount(0);
            } else if(areaType == AreaType.Continent)
            {
                stringSelect =
                "SELECT SUM(Population) as pop FROM country "
                + "WHERE Continent = '" + name + "'";
                popc.SetCount(1);
            } else if(areaType == AreaType.Region)
            {
                stringSelect =
                        "SELECT SUM(Population) as pop FROM country "
                                + "WHERE Region = '" + name + "'";
                popc.SetCount(2);
            } else if(areaType == AreaType.Country)
            {
                stringSelect =
                        "SELECT SUM(Population) as pop FROM country "
                                + "WHERE Name = '" + name + "'";
                popc.SetCount(3);
            } else if(areaType == AreaType.District)
            {
                stringSelect =
                        "SELECT Population as pop FROM city "
                                + "WHERE District = '" + name + "'";
                popc.SetCount(4);
            } else if(areaType == AreaType.City)
            {
                stringSelect =
                        "SELECT Population as pop FROM city "
                                + "WHERE Name = '" + name + "'";
                popc.SetCount(5);
            }
    /*
            //"SELECT Population, COUNT(Population) AS pop, Code "
            //+"FROM country ";
            // +"GROUP BY Population, Code "
            //+"UNION ALL "
            //+"SELECT 'SUM' Population, COUNT(Population) "
            //+"FROM country ";
            //+"GROUP BY Population";

                    //"SELECT SUM(Population) as pop FROM country "
                            //+ "WHERE " + AreaType.Continent + " = 'Europe'";
                            */
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(stringSelect);
            // Check one is returned
            if (rset.next())
            {
                popc.SetWorldPop(rset.getString("pop"));
                return popc;
            }
            else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get 1 or more Populations");
            return null;
        }
    }

    public void displayPopWorld(PopulationCategories popc, String name)
    {
        String[] text = new String[10];
        text[0] = "Population of the world: ";
        text[1] = "Population of the Continent of " + name + ": ";
        text[2] = "Population of the Region of " + name + ": ";
        text[3] = "Population of the Country of " + name + ": ";
        text[4] = "Population of the District of " + name + ": ";
        text[5] = "Population of the City of " + name + ": ";
        if (popc != null)
        {
            //System.out.println("_Simple Populations_\n");

                System.out.println(
                text[popc.GetCount()] + popc.GetWorldPop() + "\n");
                //+ "Capital: " + cnt.capital + "\n");
        }
    }

    //Displays details of a given Country object to terminal
    public void displayCountry(Country cnt)
    {
        if (cnt != null)
        {
            System.out.println("_Country Report_\n");
            System.out.println(cnt.Display());
        }
    }

    //Displays details of a given CapitalCity object to terminal
    public void displayCapitalCityReport(CapitalCity cptc)
    {
        if(cptc != null)
        {
            System.out.println("_Capital City Report_\n");
            System.out.println(cptc.Display());
        }
    }

    //Displays details of a given CapitalCity object to terminal
    public void displayCityReport(CityReport city)
    {
        if(city != null)
        {
            System.out.println("_City Report_\n");
            System.out.println(city.Display());
        }
    }

    public void displayPopulation(List<Population> popList)
    {
        if (popList != null)
        {
            System.out.println("_Population Report_\n");
            StringBuilder printString = new StringBuilder();
            for (Population pop : popList) {
                printString.append(pop.Display());
            }
            System.out.println(printString);
        }
    }

    public void displayLanguage(List<Language> langList)
    {
        if (langList != null)
        {
            System.out.println("_Language Report_\n");
            StringBuilder printString = new StringBuilder();
            for (Language lang : langList) {
                printString.append(lang.Display());
            }
            System.out.println(printString);
        }
    }
}