package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

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
    void testGetEmployee()
    {
        Country cnt = app.getCountry("CYM");
        String expected =  "CYM" + " " + "Cayman Islands" + "\n" +
            "Continent: " + "North America" + "\n" +
            "Region: " + "Caribbean" + "\n" +
            "Capital: " + "George Town" + "\n" +
            "Population: " + "38000" + "\n\n";
        assertEquals(expected, cnt.Display());
    }
}
