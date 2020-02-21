package com.napier.sem;

public class SortedPopulation {
    private String name, continent;
    private int population;
    private double doublePop;

    //Constructor //there are multiple constructors when they are used for different situations, the String variable continent is not needed in every call of the class constructor
    public SortedPopulation(String name, int population, String continent)
    {
        this.name = name;
        this.population = population;
        this.continent = continent;
    }

    public SortedPopulation(String continent, double population)
    {
        this.continent = continent;
        this.doublePop = population;
    }

    public SortedPopulation(String name, int population)
    {
        this.name = name;
        this.population = population;
    }

    public String DisplayWorld() //multiple display methods are used in different situations when the string accompanying the output is different
    {
        String displayString = "Population of " + name + ": " + population + "\n";
        return displayString;
    }

    public String DisplayContinent()
    {
        String displayString = "Population of the country of " + name + " In the continent of " + continent + ": " + population + "\n";
        return displayString;
    }

    public String DisplayRegion()
    {
        String displayString = "In the Region of " + continent + " the population of " + name + " is: " + population + "\n";
        return displayString;
    }

    public String DisplayCity()
    {
        String displayString = "Population of the city of " + name + " is: " + population + "\n";
        return displayString;
    }

    public String DisplayCityContinent()
    {
        String displayString = "Population of the city of " + name + " in the continent of " + continent + " is: " + population + "\n";
        return displayString;
    }

    public String DisplayCityCountry()
    {
        String displayString = "Population of the city of " + name + " In the country of " + continent + " is: " + population + "\n";
        return displayString;
    }

    public String DisplayCityDistrict()
    {
        String displayString = "Population of the city of " + name + " In the district of " + continent + " is: " + population + "\n";
        return displayString;
    }
}
