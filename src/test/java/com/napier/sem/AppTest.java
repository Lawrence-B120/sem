package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    //**************************************************Get Country
    @Test
    void getCountryTestNull()
    {
        app.getCountry(null);
    }
    @Test
    void getCountryTestAllCorrect()
    {
        app.getCountry("LTU");
    }

    //**************************************************Get Capital City
    @Test
    void getCapitalCityTestNull()
    {
        app.getCapitalCity(null);
    }
    @Test
    void getCapitalCityTestAllCorrect()
    {
        app.getCapitalCity("LTU");
    }

    //**************************************************Get City
    @Test
    void getCityTestNull()
    {
        app.getCity(null);
    }
    @Test
    void getCityTestAllCorrect()
    {
        app.getCity("LTU");
    }

    //**************************************************Get Population
    @Test
    void getPopulationTestNull()
    {
        app.getPopulation(null, 0);
    }
    @Test
    void getPopulationTestAllCorrect() { app.getPopulation(App.AreaType.Country, 10); }

    //**************************************************Get Language
    @Test
    void getLanguageTestAllCorrect() { app.getLanguage(); }

    //**************************************************Get Populations
    @Test
    void getPopulationsTestNull() { app.getPopulations(null, null); }
    @Test
    void getPopulationsTestAllCorrect() { app.getPopulations(App.AreaType.Country, "LTU"); }

    //**************************************************Get Sorted Pop
    @Test
    void getSortedPopTestNull() { app.getSortedPop(null, null, null, 10); }
    @Test
    void getSortedPopTestAllCorrect() { app.getSortedPop(App.AreaType.Continent, App.RequestType.noRequest, "LTU", 10); }

    //**************************************************Display Pop World
    @Test
    void displayPopWorldTestNull() { app.displayPopWorld(null, null); }
    @Test
    void displayPopWorldTestAllCorrect() {
        PopulationCategories popc = new PopulationCategories();
        popc.SetWorldPop("100");
        popc.SetCount(1);
        app.displayPopWorld(popc, "name");
    }

    //**************************************************Display Country
    @Test
    void displayCountryTestNull() { app.displayCountry(null); }
    @Test
    void displayCountryTestAllCorrect() {
        Country cnt = new Country("COD", "Name", "Continent", "Region", "Capital", 100);
        app.displayCountry(cnt);
    }

    //**************************************************Display Capital City
    @Test
    void displayCapitalCityReportTestNull() { app.displayCapitalCityReport(null); }
    @Test
    void displayCapitalCityReportTestAllCorrect() {
        CapitalCity cptc = new CapitalCity("Name", "Country", "District", 100, 1);
        app.displayCapitalCityReport(cptc);
    }

    //**************************************************Display City
    @Test
    void displayCityReportTestNull() { app.displayCityReport(null); }
    @Test
    void displayCityReportTestAllCorrect() {
        CityReport city = new CityReport("name", "country", "district", 100);
        app.displayCityReport(city);
    }

    //**************************************************Display Population
    @Test
    void displayPopulationTestNull() { app.displayPopulation(null); }
    @Test
    void displayPopulationTestNullFilledList() {
        List<Population> poplist = new LinkedList<Population>();
        poplist.add(null);
        app.displayPopulation(poplist);
    }
    @Test
    void displayPopulationTestAllCorrect() {
        List<Population> poplist = new LinkedList<Population>();
        poplist.add(new Population("name", "1000", "500", "500", 50, 50));
        app.displayPopulation(poplist);
    }

    //**************************************************Display Language
    @Test
    void displayLanguageTestNull() { app.displayLanguage(null); }
    @Test
    void displayLanguageTestNullFilledList() {
        List<Language> langList = new LinkedList<Language>();
        langList.add(null);
        app.displayLanguage(langList);
    }
    @Test
    void displayLanguageTestAllCorrect() {
        List<Language> langList = new LinkedList<Language>();
        langList.add(new Language("name", 1000, 10));
        app.displayLanguage(langList);
    }

    //**************************************************Display Sorted Pop
    @Test
    void displaySortedPopTestNull() { app.displaySortedPop(null, App.AreaType.Country, App.RequestType.Continent); }
    @Test
    void displaySortedPopTestNullFilledList() {
        List<SortedPopulation> poplist = new LinkedList<SortedPopulation>();
        poplist.add(null);
        app.displaySortedPop(poplist, App.AreaType.Country, App.RequestType.Continent);
    }
    @Test
    void displaySortedPopTestAllCorrect() {
        List<SortedPopulation> poplist = new LinkedList<SortedPopulation>();
        poplist.add(new SortedPopulation("name", 1000, "continent"));
        app.displaySortedPop(poplist, App.AreaType.Country, App.RequestType.Continent);
    }
}
