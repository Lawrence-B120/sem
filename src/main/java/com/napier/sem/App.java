package com.napier.sem;

import java.sql.*;

import com.napier.sem.gui.Gui;
import com.napier.sem.gui.GuiListener;


public class App
{
    private Connection  con = null;
    private static final Gui gui = new Gui();

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Display Example Country
        a.displayCountry(a.getCountry("ABW"));

        // Disconnect from database
        a.disconnect();

        new GuiListener();
    }

   public static Gui getGui()
   {
       return  gui;
   }

    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
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
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    //Get the details of a country from the database
    public Country getCountry(String ID)
    {
        try
        {
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
            if (rset.next())
            {
                Country cnt = new Country();
                cnt.code = rset.getString("Code");
                cnt.name = rset.getString("country.Name");
                cnt.continent = rset.getString("Continent");
                cnt.region = rset.getString("Region");
                cnt.capital = rset.getString("city.Name");
                cnt.population = rset.getInt("country.Population");
                return cnt;
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

}