package com.napier.sem;

public class PopulationCategories {
    private String code;
    private String worldPop, test1, test2;

    public String GetWorldPop()
    {
        return worldPop;
        //test comment
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
