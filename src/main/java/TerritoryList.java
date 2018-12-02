public class TerritoryList
{   
    private class Continent
    {
            private final String name;
            private Territory territories[];
            private int pos;
            private final int value;
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public Continent(String Name, int tNum, int val)
            {
                    name = Name;
                    pos = 0;
                    territories = new Territory[tNum];
                    territories[0] = null;
                    value = val;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public Territory[] getTer()
            {
                return territories;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public String getCont()
            {
                return name;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public boolean full()
            {
                if(pos < territories.length)
                        return false;
                return true;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public int getValue()
            {
                return value;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public void add(Territory t)
            {
                    if(!full())
                            territories[pos] = t;
                    else
                            System.out.printf("%s is at max capacity%n", name);
                    pos++;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public void remove(Territory t)
            {
                    if(territories[0] != null)
                    {
                            int i;
                            for(i = 0; i < pos && territories[i] != t; i++){}
                            if( i < pos)
                            {
                                    territories[i] = null;
                                    territories[i] = territories[--pos];
                            }
                            else
                                    System.out.printf("%s is not owned by you%n", t.getTerritoryName());
                    }
                    else
                            System.out.printf("%s is empty%n", name);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public int getPos()
            {
                return pos;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public boolean has(Territory t)
            {
                for(int i = 0; i < pos; i++)
                {
                    if(territories[i] == t)
                        return true;
                }
                return false;
            }

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private int territoryCount = 0;
    private Continent continents[] = new Continent[6];
    private int getContinentIndex(String c)
    {
        int index=-1;
        switch(c)
        {
            case "North America":
                    index = 0;
                    break;
            case "South America":
                    index = 1;
                    break;
            case "Europe":
                    index = 2;
                    break;
            case "Africa":
                    index = 3;
                    break;
            case "Asia":
                    index = 4;
                    break;
            case "Australia":
                    index = 5;
                    break;
        }
        return index;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TerritoryList()
    {
        continents[0] = new Continent("North America", 9, 5);
        continents[1] = new Continent("South America", 4, 2);
        continents[2] = new Continent("Europe", 7, 5);
        continents[3] = new Continent("Africa", 6, 3);
        continents[4] = new Continent("Asia", 12, 7);
        continents[5] = new Continent("Australia", 4, 2);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addTerritory(Territory t)
    {
        continents[getContinentIndex(t.getContinent().toString())].add(t);
        territoryCount++;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void removeTerritory(Territory t)
    {
        continents[getContinentIndex(t.getContinent().toString())].remove(t);
        territoryCount--;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int numTerritoriesCaptured()
    {
        int capt = 0;
        for(int i = 0; i < 6; i++)
        {
            if(continents[i].full())
               capt++;
        }
        return capt;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean hasTerritory(Territory t)
    {
        for(int i = 0; i < 6; i++)
        {
            if(continents[i].has(t))
                return true;
        }
        return false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getTerritoryCount()
    {
        return territoryCount;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int coninentValues()
    {
        int v = 0;
        for(int i = 0; i < 6; i++)
        {
            if(continents[i].full())
                v += continents[i].getValue();
        }
        return v;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void printConts()
    {
        Territory ters[];
        for(int i = 0, j = 1; i < 6; i++)
        {
            ters = continents[i].getTer();
            if(continents[i].getPos() > 0)
            {
                System.out.printf("%s:%n", continents[i].getCont());
                for(int k = 0; k < continents[i].getPos(); k++)
                {
                    System.out.printf("%d: %s", j, ters[i].getTerritoryName());
                }
            }
        }
    }
		
}



