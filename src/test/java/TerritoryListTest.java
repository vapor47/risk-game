import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TerritoryListTest {

	static Map<String,Territory> territories = new HashMap<>();
	@Test
	public void addRemoveTerritoryTest() 
	{
		createTerritories();
		TerritoryList tlist = new TerritoryList();
				
		int i = 1;
		for(Territory t : territories.values())
		{
			tlist.addTerritory(t);
			if(tlist.getTerritoryCount() != i)
				fail("Territory Count Is Bad");
			
			if(!tlist.hasTerritory(t))
				fail("Error in hasTerritory()");
			i++;
		}
		
		if(tlist.numContinentsCaptured() != 6)
			fail("Error in numContinentsCaptured()");
		
		tlist.printConts();
		
		if(tlist.continentValues() != 24)
			fail("Error in continentValues()");
		
		i--;
		
		for(Territory t : territories.values())
		{
			tlist.removeTerritory(t);
			
			if(tlist.getTerritoryCount() != (--i))
				fail("Territory Count or removeTerritory Is Bad");
			
			if(tlist.hasTerritory(t))
				fail("Error in hasTerritory() or removeTerritory");
		}
		
		if(tlist.numContinentsCaptured() != 6)
			fail("Error in numContinentsCaptured()");
		
		if(tlist.continentValues() != 0)
			fail("Error in continentValues()");
	}
	
	
	private void createTerritories(){
        // North America
        territories.put("Alaska", new Territory(TerritoryName.ALASKA, Continent.NORTH_AMERICA,
                new String[]{"Alberta","Kamchatka","North West Territory"}));
        territories.put("Alberta", new Territory(TerritoryName.ALBERTA, Continent.NORTH_AMERICA,
                new String[]{"Alaska","North West Territory","Ontario","Western United States"}));
        territories.put("Central America", new Territory(TerritoryName.CENTRAL_AMERICA, Continent.NORTH_AMERICA,
                new String[]{"Eastern United States","Venezuela","Western United States"}));
        territories.put("Eastern United States", new Territory(TerritoryName.EASTERN_UNITED_STATES, Continent.NORTH_AMERICA,
                new String[]{"Central America","Ontario","Quebec","Western United States"}));
        territories.put("Greenland", new Territory(TerritoryName.GREENLAND, Continent.NORTH_AMERICA,
                new String[]{"Iceland","North West Territory","Ontario","Quebec"}));
        territories.put("North West Territory", new Territory(TerritoryName.NORTHWEST_TERRITORY, Continent.NORTH_AMERICA,
                new String[]{"Alaska","Alberta","Greenland","Ontario"}));
        territories.put("Ontario", new Territory(TerritoryName.ONTARIO, Continent.NORTH_AMERICA,
                new String[]{"Alberta","Eastern United States","Greenland","North West Territory","Quebec","Western United States"}));
        territories.put("Quebec", new Territory(TerritoryName.QUEBEC, Continent.NORTH_AMERICA,
                new String[]{"Eastern United States","Greenland","Ontario"}));
        territories.put("Western United States", new Territory(TerritoryName.WESTERN_UNITED_STATES, Continent.NORTH_AMERICA,
                new String[]{"Alberta","Central America","Eastern United States","Ontario"}));

        // South America
        territories.put("Argentina", new Territory(TerritoryName.ARGENTINA, Continent.SOUTH_AMERICA,
                new String[]{"Brazil","Peru"}));
        territories.put("Brazil", new Territory(TerritoryName.BRAZIL, Continent.SOUTH_AMERICA,
                new String[]{"Argentina","North Africa","Peru","Venezuela"}));
        territories.put("Peru", new Territory(TerritoryName.PERU, Continent.SOUTH_AMERICA,
                new String[]{"Argentina","Brazil","Venezuela"}));
        territories.put("Venezuela", new Territory(TerritoryName.VENEZUELA, Continent.SOUTH_AMERICA,
                new String[]{"Brazil","Central America","Peru"}));

        // Europe
        territories.put("Great Britain", new Territory(TerritoryName.GREAT_BRITAIN, Continent.EUROPE,
                new String[]{"Iceland","Northern Europe","Scandinavia","Western Europe"}));
        territories.put("Iceland", new Territory(TerritoryName.ICELAND, Continent.EUROPE,
                new String[]{"Great Britain","Greenland","Scandinavia"}));
        territories.put("Northern Europe", new Territory(TerritoryName.NORTHERN_EUROPE, Continent.EUROPE,
                new String[]{"Great Britain","Scandinavia","Southern Europe","Ukraine","Western Europe"}));
        territories.put("Scandinavia", new Territory(TerritoryName.SCANDINAVIA, Continent.EUROPE,
                new String[]{"Great Britain","Iceland","Northern Europe","Ukraine"}));
        territories.put("Southern Europe", new Territory(TerritoryName.SOUTHERN_EUROPE, Continent.EUROPE,
                new String[]{"Egypt","Middle East","North Africa","Northern Europe","Ukraine","Western Europe"}));
        territories.put("Ukraine", new Territory(TerritoryName.UKRAINE, Continent.EUROPE,
                new String[]{"Afghanistan","Middle East","Northern Europe","Scandinavia","Southern Europe","Ural"}));
        territories.put("Western Europe", new Territory(TerritoryName.WESTERN_EUROPE, Continent.EUROPE,
                new String[]{"Great Britain","North Africa","Northern Europe","Southern Europe"}));

        // Africa
        territories.put("Congo", new Territory(TerritoryName.CONGO, Continent.AFRICA,
                new String[]{"East Africa","North Africa","South Africa"}));
        territories.put("East Africa", new Territory(TerritoryName.EAST_AFRICA, Continent.AFRICA,
                new String[]{"Congo","Egypt","Madagascar","Middle East","North Africa","South Africa"}));
        territories.put("Egypt", new Territory(TerritoryName.EGYPT, Continent.AFRICA,
                new String[]{"East Africa","Middle East","North Africa","Southern Europe"}));
        territories.put("Madagascar", new Territory(TerritoryName.MADAGASCAR, Continent.AFRICA,
                new String[]{"East Africa","South Africa"}));
        territories.put("North Africa", new Territory(TerritoryName.NORTH_AFRICA, Continent.AFRICA,
                new String[]{"Brazil","Congo","East Africa","Egypt","Southern Europe","Western Europe"}));
        territories.put("South Africa", new Territory(TerritoryName.SOUTH_AFRICA, Continent.AFRICA,
                new String[]{"Congo","East Africa","Madagascar"}));

        // Asia
        territories.put("Afghanistan", new Territory(TerritoryName.AFGHANISTAN, Continent.ASIA,
                new String[]{"China","India","Middle East","Ukraine","Ural"}));
        territories.put("China", new Territory(TerritoryName.CHINA, Continent.ASIA,
                new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"}));
        territories.put("India", new Territory(TerritoryName.INDIA, Continent.ASIA,
                new String[]{"Afghanistan","China","Middle East","Siam"}));
        territories.put("Irkutsk", new Territory(TerritoryName.IRKUTSK, Continent.ASIA,
                new String[]{"Kamchatka","Mongolia","Siberia","Yakutsk"}));
        territories.put("Japan", new Territory(TerritoryName.JAPAN, Continent.ASIA,
                new String[]{"Kamchatka","Mongolia"}));
        territories.put("Kamchatka", new Territory(TerritoryName.KAMCHATKA, Continent.ASIA,
                new String[]{"Alaska","Irkutsk","Japan","Mongolia","Yakutsk"}));
        territories.put("Middle East", new Territory(TerritoryName.MIDDLE_EAST, Continent.ASIA,
                new String[]{"Afghanistan","East Africa","Egypt","India","Southern Europe","Ukraine"}));
        territories.put("Mongolia", new Territory(TerritoryName.MONGOLIA, Continent.ASIA,
                new String[]{"China","Irkutsk","Japan","Kamchatka","Siberia"}));
        territories.put("Siam", new Territory(TerritoryName.SIAM, Continent.ASIA,
                new String[]{"China","India","Indonesia"}));
        territories.put("Siberia", new Territory(TerritoryName.SIBERIA, Continent.ASIA,
                new String[]{"China","Irkutsk","Mongolia","Ural","Yakutsk"}));
        territories.put("Ural", new Territory(TerritoryName.URAL, Continent.ASIA,
                new String[]{"Afghanistan","China","Siberia","Ukraine"}));
        territories.put("Yakutsk", new Territory(TerritoryName.YAKUTSK, Continent.ASIA,
                new String[]{"Irkutsk","Kamchatka","Siberia"}));

        // Australia
        territories.put("Eastern Australia", new Territory(TerritoryName.EASTERN_AUSTRALIA, Continent.AUSTRALIA,
                new String[]{"New Guinea","Western Australia"}));
        territories.put("Indonesia", new Territory(TerritoryName.INDONESIA, Continent.AUSTRALIA,
                new String[]{"New Guinea","Siam","Western Australia"}));
        territories.put("New Guinea", new Territory(TerritoryName.NEW_GUINEA, Continent.AUSTRALIA,
                new String[]{"Eastern Australia","Indonesia","Western Australia"}));
        territories.put("Western Australia", new Territory(TerritoryName.WESTERN_AUSTRALIA, Continent.AUSTRALIA,
                new String[]{"Eastern Australia","Indonesia","New Guinea"}));
    }

}