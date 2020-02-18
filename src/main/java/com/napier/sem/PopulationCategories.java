package com.napier.sem;

public class PopulationCategories {
    private String code;
    private String worldPop;

    public String GetWorldPop()
    {
        return worldPop;
    }

    public String GetCountryCode()
    {
        return code;
    }

    public void SetWorldPop(String n)
    {
        worldPop = n;
    }
}
