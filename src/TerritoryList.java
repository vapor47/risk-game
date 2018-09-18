import java.util.Scanner;
import java.util.Arrays;

public class TerritoryList
{	
	public class Continent
	{
		String name;
		String territories[];
		int pos;
		public Continent(Name, tNum)
		{
			name = Name;
			pos = 0;
			territories = new String[tNum]
			territories[0] = "";
		}
		
		public boolean full()
		{
			if(pos < territories.length)
				return false;
			return true;
		}
		
		public void add(string t)
		{
			if(!full())
				territories[pos] = t;
			else
				System.out.printf("%s is at max capacity%n", name);
			pos++;
		}
		
		public void remove(string t)
		{
			if(territories[0] != "")
			{
				int i;
				for(i = 0; i < pos && territories[i] != ; i++){}
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
		
	}
	
	private Continent[] = new Continent[6];
	private int getContinentIndex(string c)
		{
			int index
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
		
	public TerritoryList()
	{
		Continent[0] = new Continent("North America", 9);
		Continent[1] = new Continent("South America", 2);
		Continent[2] = new Continent("Europe", 5);
		Continent[3] = new Continent("Africa", 3);
		Continent[4] = new Continent("Asia", 7);
		Continent[5] = new Continent("Australia", 2);
	}
	
	public void addTerritory(String c, String t)
	{
		this.Continent[getContinentIndex(c)].add(t);
	}
	public void removeTerritory(String c, String t)
	{
		this.Continent[getContinentIndex(c)].remove(t);
	}
	public void numTerritoriesCaptured()
	{
		int capt = 0;
		for(int i = 0; i < 6; i++)
		{
			if(Continent[i].full)
				capt++;
		}
		return capt;
	}
			
}