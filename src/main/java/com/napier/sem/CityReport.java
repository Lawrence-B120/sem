package com.napier.sem;

public class CityReport {

    private String name, country, district;
    private int population;

    //Constructor
    public CityReport(String name, String country, String district, int population)
    {
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
    }

    public String Display()
    {
        String displayString = name + "\n" +
                "Country: " + country + "\n" +
                "District: " + district + "\n" +
                "Population: " + population + "\n\n";
        return displayString;
    }
}
