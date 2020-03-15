package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060");
    }

    @Test
    void getCountryTest()
    {
        Country cnt = app.getCountry("CYM");
        String expected =  "CYM" + " " + "Cayman Islands" + "\n" +
            "Continent: " + "North America" + "\n" +
            "Region: " + "Caribbean" + "\n" +
            "Capital: " + "George Town" + "\n" +
            "Population: " + "38000" + "\n\n";
        assertEquals(expected, cnt.Display(), "Country from database had unexpected values :(");
    }

    @Test
    void getCapitalCityTest()
    {
        CapitalCity cptc = app.getCapitalCity("CYM");
        String expected =  "George Town" + "\n" +
                "Country: " + "Cayman Islands" + "\n" +
                "District: " + "Grand Cayman" + "\n" +
                "Population: " + "19600" + "\n\n";
        assertEquals(expected, cptc.Display(), "Capital City from database had unexpected values :(");
    }

    @Test
    void getCityTest()
    {
        CityReport city = app.getCity("553");
        String expected =  "George Town" + "\n" +
                "Country: " + "Cayman Islands" + "\n" +
                "District: " + "Grand Cayman" + "\n" +
                "Population: " + "19600" + "\n\n";
        assertEquals(expected, city.Display(), "City from database had unexpected values :(");
    }

    @Test
    void getPopulationTest()
    {
        List<Population> poplist = app.getPopulation(App.AreaType.Country, 1);
        String expected =  "Afghanistan\n" +
                "Total Population: 22720000\n" +
                "City Population: 2332100 (10.2645%)\n" +
                "Non-City Population: 20387900 (89.7355%)\n\n";
        assertEquals(expected, poplist.get(0).Display(), "Population report from database had unexpected values :(");
    }

    @Test
    void getLanguageTest()
    {
        List<Language> langlist = app.getLanguage();
        String expected =  "Chinese\n" +
                "Speaking Population: 1191843539\n" +
                "Percentage of World: 19.606722%\n" +
                "\n";
        assertEquals(expected, langlist.get(0).Display(), "Language report from database had unexpected values :(");
    }

    @Test
    void getPopulationsTest()
    {
        PopulationCategories popc = app.getPopulations(App.AreaType.World, "");
        String expected =  "6078749450";
        assertEquals(expected, popc.GetWorldPop(), "Area Population report from database had unexpected values :(");
    }

    @Test
    void getSortedPopTest()
    {
        List<SortedPopulation> poplist = app.getSortedPop(App.AreaType.World, App.RequestType.noRequest, "", 1);
        String expected =  "Population of China: 1277558000\n";
        assertEquals(expected, poplist.get(0).DisplayWorld(), "Sorted Population report from database had unexpected values :(");
    }
}
