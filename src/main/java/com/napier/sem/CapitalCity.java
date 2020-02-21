package com.napier.sem;

//Class containing variables for Capital City details
public class CapitalCity {

    //Displayed Capital City details
    private String name, country, district;
    private int population, id;

    //Constructor
    public CapitalCity(String name, String country, String district, int population, int id)
    {
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
        this.id = id;
    }

    public String Display() //this method is called in the main method to display the variables and their values
    {
        String displayString = name + "\n" +
                "Country: " + country + "\n" +
                "District: " + district + "\n" +
                "Population: " + population + "\n\n";
        return displayString;
    }
}