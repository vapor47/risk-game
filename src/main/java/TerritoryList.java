public class TerritoryList
{   
    public class Continent
    {
            private final String name;
            private String territories[];
            private int pos;
            private final int value;
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public Continent(String Name, int tNum, int val)
            {
                    name = Name;
                    pos = 0;
                    territories = new String[tNum];
                    territories[0] = "";
                    value = val;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public String[] getTer()
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
            public int getValue()
            {
                return value;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public void add(String t)
            {
                    if(!full())
                            territories[pos] = t;
                    else
                            System.out.printf("%s is at max capacity%n", name);
                    pos++;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            public void remove(String t)
            {
                    if(!territories[0].equals(""))
                    {
                            int i;
                            for(i = 0; i < pos && !territories[i].equals(t); i++){}
                            if( i < pos)
                            {
                                    territories[i] = "";
                                    territories[i] = territories[--pos];
                            }
                            else
                                    System.out.printf("%s is not in %s%n", t, name);
                    }
                    else
                            System.out.printf("%s is empty%n", name);
            }
            
            public int getPos()
            {
                return pos;
            }
            public boolean has(String t)
            {
                for(int i = 0; i < pos; i++)
                {
                    if(territories[i].equals(t))
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
    public void addTerritory(String c, String t)
    {
        continents[getContinentIndex(c)].add(t);
        territoryCount++;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void removeTerritory(String c, String t)
    {
        continents[getContinentIndex(c)].remove(t);
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
    public boolean hasTerritory(String t)
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
        String ters[];
        for(int i = 0, j = 1; i < 6; i++)
        {
            ters = continents[i].getTer();  // territory array = territory array in the player's controlled continent
            if(continents[i].getPos() > 0)  
            {
                System.out.printf("%s:%n", continents[i].getCont());
                for(int k = 0; k < continents[i].getPos(); k++)
                {
                    System.out.printf("%d: %s", j, ters[i]);                    
                }
            }
        }
    }
		
}



