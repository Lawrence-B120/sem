package com.napier.sem;

//Class containing variables for Country details
public class Country {

    //Displayed Country details
    private String code, name, continent, region, capital;
    private int population;

    //Constructor
    public Country(String code, String name, String continent, String region, String capital, int population)
    {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.capital = capital;
        this.population = population;
    }

    public String Display()
    {
        String displayString = code + " " + name + "\n" +
                "Continent: " + continent + "\n" +
                "Region: " + region + "\n" +
                "Capital: " + capital + "\n" +
                "Population: " + population + "\n\n";
        return displayString;
    }
}