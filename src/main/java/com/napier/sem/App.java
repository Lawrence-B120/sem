package com.napier.sem;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
    public enum AreaType {
        World,
        Continent,
        Region,
        Country,
        District,
        City,
        Capital,
        Cityliving
    }

    public enum RequestType {
        World,
        Continent,
        Region,
        Country,
        District,
        City,
        noRequest
    }

    private Connection con = null;

    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        if (args.length < 1)
        {
            a.connect("localhost:33060");
        }
        else
        {
            a.connect(args[0]);
        }
        // Display Example Country  The --displayCountry, displayCityReport and displayCapitalCityReport-- method takes in an instance of country and the method of getCountry takes in a string ID
        //which is used in the Select statement to get an ID from the database
        a.displayCountry(a.getCountry("ABW"));

        //display example of city report
        a.displayCityReport(a.getCity("JPN"));

        //display example capital city report
        a.displayCapitalCityReport(a.getCapitalCity("GBR"));

        //Display language report
        a.displayLanguage(a.getLanguage());

        //Display Population of world The --displayPopWorld and getPopulations methods, take in an instance of  PopulationCategories class
        // and then takes in an Area type which decides which if statement to execute for showing either continents or regions etc
        //The name is the name of the region or continent, both methods take in these variables as the string used in the output will change depending on the values of the variables
        System.out.println("_Simple Populations_\n");
        a.displayPopWorld(a.getPopulations(AreaType.World,""), "");
        a.displayPopWorld(a.getPopulations(AreaType.Continent,"Asia"), "Asia");
        a.displayPopWorld(a.getPopulations(AreaType.Region, "Australia and New Zealand"), "Australia and New Zealand");
        a.displayPopWorld(a.getPopulations(AreaType.Country, "France"), "France");
        a.displayPopWorld(a.getPopulations(AreaType.District, "Chaco"), "Chaco");
        a.displayPopWorld(a.getPopulations(AreaType.City, "Edinburgh"), "Edinburgh");

        //Display sorted population results
        //Sorted Pop takes in an Area type and Request type which decides which if statement to run as well as which sql query to try.
        //The limit variable decides how many rows will be displayed.
        a.displaySortedPop(a.getSortedPop(AreaType.World, RequestType.noRequest, "", 5), AreaType.World, RequestType.noRequest);
        a.displaySortedPop(a.getSortedPop(AreaType.Continent, RequestType.noRequest, "Europe", 5), AreaType.Continent, RequestType.noRequest);
        a.displaySortedPop(a.getSortedPop(AreaType.Region, RequestType.noRequest, "Southern Europe", 5), AreaType.Region, RequestType.noRequest);
        a.displaySortedPop(a.getSortedPop(AreaType.City, RequestType.World, "", 5), AreaType.City, RequestType.World);
        a.displaySortedPop(a.getSortedPop(AreaType.City, RequestType.Continent, "North America", 5), AreaType.City, RequestType.Continent);
        a.displaySortedPop(a.getSortedPop(AreaType.City, RequestType.Region, "Polynesia", 5), AreaType.City, RequestType.Region);
        a.displaySortedPop(a.getSortedPop(AreaType.City, RequestType.Country, "France", 5), AreaType.City, RequestType.Country);
        a.displaySortedPop(a.getSortedPop(AreaType.City, RequestType.District, "Aleppo", 5), AreaType.City, RequestType.District);
        a.displaySortedPop(a.getSortedPop(AreaType.Capital, RequestType.World, "", 5), AreaType.Capital, RequestType.World);
        a.displaySortedPop(a.getSortedPop(AreaType.Capital, RequestType.Continent, "Asia", 5), AreaType.Capital, RequestType.Continent);
        a.displaySortedPop(a.getSortedPop(AreaType.Capital, RequestType.Region, "Central America", 5), AreaType.Capital, RequestType.Region);

        //display Population report of people living and not living in cities
        a.displayPopulation(a.getPopulation(AreaType.Continent, 5));
        a.displayPopulation(a.getPopulation(AreaType.Region, 5));
        a.displayPopulation(a.getPopulation(AreaType.Country, 5));

        // Disconnect from database
        a.disconnect();
    }

    public void connect(String location) {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
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
    public CapitalCity getCapitalCity(String ID) { //the try catch method stops critical errors from occurring withe queries
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
            // Check one is returned
            if (rset.next()) {  //if there is another row output from the wuery then run this code
                CapitalCity cptc = new CapitalCity(rset.getString("city.Name"),
                        rset.getString("country.Name"), //This sets variables from the sql statement so they can be used in the output on the terminal
                        rset.getString("District"),
                        rset.getInt("city.Population"),
                        rset.getInt("ID"));
                return cptc; //if there are still results return the instance of the Capital city class
            } else
                return null; //if there are no more rows to display then rteun null
        } catch (Exception e) {
            System.out.println(e.getMessage()); //this displays the error message in the program, then provides an output message saying that it failed
            System.out.println("Failed to get Capital City details");
            return null;
        }
    }

    //Get cities from the database
    public CityReport getCity(String ID)
    {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.ID, city.Name, CountryCode, District, city.Population, country.Capital, country.Name "
                            + "FROM city JOIN country "
                            + "ON country.Code=city.CountryCode "
                            + "WHERE city.ID = '" + ID + "'";
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
    public List<Population> getPopulation(AreaType areaType, int limit)
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
            strSelect += " LIMIT " + limit;
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

    public PopulationCategories getPopulations(AreaType areaType, String name) //Displays the popualtion report with general outputs
    {                                                       //population of the world, continent, region, district and city
        PopulationCategories popc = new PopulationCategories();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String stringSelect = "";
            if(areaType == AreaType.World) //Depending on the area type chosen in the main method, a specific if statmenet will run
            {
                stringSelect =
                        "SELECT SUM(Population) as pop FROM country ";
                popc.SetCount(0); //the count variable is used when displaying the text to accompany the query result
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

    public List<SortedPopulation> getSortedPop(AreaType areaType, RequestType requestType, String name, int limit) {
        try { //this method displays a range of outputs depending on the variable inputs such as AreaType, limit, RequestType and name
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String stringSelect = ""; //intialising the variable stringSelect to a default blank
            if(areaType == AreaType.World && requestType == RequestType.noRequest) //all countries in the world org by large to small
            {
                stringSelect =
                        "SELECT Name, Population as pop, Continent FROM country " +
                                "ORDER BY pop Desc";
            } else if(areaType == AreaType.Continent && requestType == RequestType.noRequest) //all countries ina  continent org by Large to small
            {
                stringSelect =
                        "SELECT Name as name, Continent as continent, SUM(Population) as pop FROM country "
                                + "WHERE Continent = '" + name + "' "
                                + "GROUP BY name ORDER BY pop desc";
            } else if(areaType == AreaType.Region && requestType == RequestType.noRequest) //All the countries in a region org by Large to small
            {
                stringSelect =
                        "SELECT Name as name, Region as region, SUM(Population) as pop FROM country "
                                + "WHERE Region = '" + name + "' "
                                + "GROUP BY name ORDER BY pop desc";

            } else if(areaType == AreaType.City && requestType == RequestType.World) //ALL cities in the world org by pop large to small
            {
                stringSelect =
                        "SELECT Name as name, SUM(Population) as pop FROM city "
                        + "GROUP BY name ORDER BY pop desc";
            } else if(areaType == AreaType.City && requestType == RequestType.Continent) //All cities in a continent org by Large to Small
            {
                stringSelect =
                        "SELECT x.Name as name, x.Population as pop, y.Continent as continent FROM city x "
                        + "JOIN country y ON y.Capital=x.ID WHERE continent = '" + name + "' "
                        + "ORDER BY pop desc";
            } else if(areaType == AreaType.City && requestType == RequestType.Region) //All cities in a region org by pop large to small
            {
                stringSelect =
                        "SELECT x.Name as name, x.Population as pop, y.Region as region FROM city x "
                        + "JOIN country y ON y.Capital=x.ID WHERE region = '" + name + "' "
                        + "ORDER BY pop desc";
            } else if(areaType == AreaType.City && requestType == RequestType.Country) //All cities in a country organised by pop large to small
            {
                stringSelect =
                        "SELECT x.Name as name, x.Population as pop, y.Name as country FROM city x "
                        + "JOIN country y ON y.Code=x.CountryCode WHERE y.name = '" + name + "' "
                        + "ORDER BY pop desc";
            } else if(areaType == AreaType.City && requestType == RequestType.District) //all cities ina district organised by pop large to small
            {
                stringSelect =
                        "SELECT Name as name, Population as pop, District as district FROM city "
                        + "WHERE district = '" + name + "' ORDER BY pop desc";
            } else if(areaType == AreaType.Capital && requestType == RequestType.World)
            {
                stringSelect =
                        "SELECT x.Name as capital, x.Population as pop FROM city x "
                        + "JOIN country y ON y.Capital=x.ID "
                        + "ORDER BY pop desc";
            } else if(areaType == AreaType.Capital && requestType == RequestType.Continent)
            {
                stringSelect =
                        "SELECT x.Name as capital, x.Population as pop, y.Continent as continent FROM city x "
                                + "JOIN country y ON y.Capital=x.ID WHERE y.Continent = '" + name + "' "
                                + "ORDER BY pop desc";
            } else if(areaType == AreaType.Capital && requestType == RequestType.Region)
            {
                stringSelect =
                        "SELECT x.Name as capital, x.Population as pop, y.Region as region FROM city x "
                                + "JOIN country y ON y.Capital=x.ID WHERE y.Region = '" + name + "' "
                                + "ORDER BY pop desc";
            }
            if(limit > 0) //if the limit variable chosen when the mehtod is called is greater than 0 then run the code below
            { //this applies a limit to the amount of rows output int he terminal
                stringSelect += " LIMIT " + limit;
            }
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(stringSelect);

            // Check one is returned
            List<SortedPopulation> popList = new LinkedList<SortedPopulation>(); //A list of results is declared for the output of the SQL query.
            if (rset.next())                        //The instance of SortedPopulation is then added as an entry to the popList variable
            {
                do {
                    if(areaType == AreaType.World) //Depending on the AreaType and RequestType variables, create an instance of the SortedPopulation class
                    {               //and initialise it with the appropriate constructor created in the SortedPopulation class.
                                    //this also sets the variables declared in the SortedPopulation class to the variable names provided in the SQL statement
                        SortedPopulation sortedPopWorld = new SortedPopulation(rset.getString("Name"), rset.getInt("pop"), rset.getString("continent"));
                        popList.add(sortedPopWorld);
                    } else if(areaType == AreaType.Continent)
                    {
                        SortedPopulation sortedPopContinent = new SortedPopulation(rset.getString("name"), rset.getInt("pop"), rset.getString("continent"));
                        popList.add(sortedPopContinent);
                    } else if(areaType == AreaType.Region)
                    {
                        SortedPopulation sortedPopRegion = new SortedPopulation(rset.getString("name"), rset.getInt("pop"), rset.getString("region"));
                        popList.add(sortedPopRegion);
                    } else if(areaType == AreaType.City && requestType == RequestType.World)
                    {
                        SortedPopulation sortedPopCity = new SortedPopulation(rset.getString("name"), rset.getInt("pop"));
                        popList.add(sortedPopCity);
                    } else if(areaType == AreaType.City && requestType == RequestType.Continent)
                    {
                        SortedPopulation sortedPopCityConti = new SortedPopulation(rset.getString("name"), rset.getInt("pop"), rset.getString("continent"));
                        popList.add(sortedPopCityConti);
                    } else if(areaType == AreaType.City && requestType == RequestType.Region)
                    {
                        SortedPopulation sortedPopCityReg = new SortedPopulation(rset.getString("name"), rset.getInt("pop"), rset.getString("region"));
                        popList.add(sortedPopCityReg);
                    } else if(areaType == AreaType.City && requestType == RequestType.Country)
                    {
                        SortedPopulation sortedPopCityCoun = new SortedPopulation(rset.getString("name"), rset.getInt("pop"), rset.getString("country"));
                        popList.add(sortedPopCityCoun);
                    } else if(areaType == AreaType.City && requestType == RequestType.District)
                    {
                        SortedPopulation sortedPopCityDist = new SortedPopulation(rset.getString("name"), rset.getInt("pop"), rset.getString("district"));
                        popList.add(sortedPopCityDist);
                    } else if(areaType == AreaType.Capital && requestType == RequestType.World)
                    {
                        SortedPopulation sortedPopCapWorld = new SortedPopulation(rset.getString("capital"), rset.getInt("pop"));
                        popList.add(sortedPopCapWorld);
                    } else if(areaType == AreaType.Capital && requestType == RequestType.Continent)
                    {
                        SortedPopulation sortedPopCapConti = new SortedPopulation(rset.getString("capital"), rset.getInt("pop"), rset.getString("continent"));
                        popList.add(sortedPopCapConti);
                    } else if(areaType == AreaType.Capital && requestType == RequestType.Region)
                    {
                        SortedPopulation sortedPopCapRegion = new SortedPopulation(rset.getString("capital"), rset.getInt("pop"), rset.getString("region"));
                        popList.add(sortedPopCapRegion);
                    }
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
            System.out.println("Failed to get Sorted Populations details");
            return null;
        }
    }

    public void displayPopWorld(PopulationCategories popc, String name) //used in conjunction with getPopulations method, controls how the output will be displayed
    {
        String[] text = new String[10]; //A string array of text to accompany query output from getPopulations
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
                text[popc.GetCount()] + popc.GetWorldPop() + "\n"); //depending on the output of the getPopulations method
                                                                    // choose the text at the position of value of the count variable in getPopulations
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
                if (pop != null)
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
                if (lang != null)
                    printString.append(lang.Display());
            }
            System.out.println(printString);
        }
    }

    public void displaySortedPop(List<SortedPopulation> popList, AreaType areaType, RequestType requestType)
    {
        if(popList != null) //if the list is not = to null then run the following code
        {
            System.out.println("_Sorted Populations_\n");
            StringBuilder printString = new StringBuilder();  //Allows us to print out multiple rows to the terminal by appending each new row to the last
            for(SortedPopulation sortedPop : popList) //this is a for each loop that counts through each row until there are no more to run
            {
                if(areaType == AreaType.World)
                {
                    printString.append(sortedPop.DisplayWorld()); //under these conditions the next row is appended to the last for each entry in the List of SortedPopulations
                } else if (areaType == AreaType.Continent)
                {
                    printString.append(sortedPop.DisplayContinent());
                } else if(areaType == AreaType.Region)
                {
                    printString.append(sortedPop.DisplayRegion());
                } else if(areaType == AreaType.City && requestType == RequestType.World)
                {
                    printString.append(sortedPop.DisplayCity());
                } else if(areaType == AreaType.City && requestType == RequestType.Continent)
                {
                    printString.append(sortedPop.DisplayCityContinent());
                } else if(areaType == AreaType.City && requestType == RequestType.Region)
                {
                    printString.append(sortedPop.DisplayRegion());
                } else if(areaType == AreaType.City && requestType == RequestType.Country)
                {
                    printString.append(sortedPop.DisplayCityCountry());
                } else if(areaType == AreaType.City && requestType == RequestType.District)
                {
                    printString.append(sortedPop.DisplayCityDistrict());
                } else if(areaType == AreaType.Capital && requestType == RequestType.World)
                {
                    printString.append(sortedPop.DisplayCity());
                } else if(areaType == AreaType.Capital && requestType == RequestType.Continent)
                {
                    printString.append(sortedPop.DisplayCityContinent());
                } else if(areaType == AreaType.Capital && requestType == RequestType.Region)
                {
                    printString.append(sortedPop.DisplayRegion());
                }
                else
                {
                    System.out.println("Invalid AreaType");
                }
            }
            System.out.println(printString); //prints the result at the end
            for(int i = 0; i < popList.size(); i++) //for each value in the list remove them until the list is clear
            {
                popList.remove(i);
            }
        } else
        {
            System.out.println("The field you are trying to access is Null");
        }
    }
}