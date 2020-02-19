package com.napier.sem;

//Class containing variables for Population details
public class Population {
    //Displayed Population details
    private String name, totalPop, cityPop, nonCityPop;
    private float cityPercent, nonCityPercent;

    //Constructor
    public Population(String name, String totalPop, String cityPop, String nonCityPop, float cityPercent, float nonCityPercent)
    {
        this.name = name;
        this.totalPop = totalPop;
        this.cityPop = cityPop;
        this.nonCityPop = nonCityPop;
        this.cityPercent = cityPercent;
        this.nonCityPercent = nonCityPercent;
    }

    public String Display()
    {
        String displayString = name + "\n"
                + "Total Population: " + totalPop + "\n"
                + "City Population: " + cityPop + " (" + cityPercent + "%)" + "\n"
                + "Non-City Population: " + nonCityPop + " (" + nonCityPercent + "%)" + "\n\n";
        return displayString;
    }
}
