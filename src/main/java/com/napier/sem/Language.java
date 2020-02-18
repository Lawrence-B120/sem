package com.napier.sem;

public class Language {
    private String name;
    private int population;
    private float percentage;

    //Constructor
    public Language(String name, int population, float percentage)
    {
        this.name = name;
        this.population = population;
        this.percentage = percentage;
    }

    public String Display()
    {
        String displayString = name + "\n"
                + "Speaking Population: " + population + "\n"
                + "Percentage of World: " + percentage + "%" + "\n\n";
        return displayString;
    }
}
