package com.napier.sem;

public class PopulationCategories {
    private String worldPop;
    private int areaTypeCount;

    public String GetWorldPop() //allows access to read the value of worldPop
    {
        return worldPop;
    }

    public int GetCount()
    {
        return areaTypeCount;
    }

    public void SetCount(int i)
    {
        areaTypeCount = i;
    }

    public void SetWorldPop(String n) //allows the value of worldPop to be set to a new value when the mthod is called and a value is provided
    {
        worldPop = n;
    }

}
