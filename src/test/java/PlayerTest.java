import java.io.ByteArrayInputStream;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    //static Territory testTerritory = new Territory(TerritoryName.valueOf("Name"), Continent.valueOf("Continent"), new String[]{"adjacentA", "adjacentB}"});
    //static Player testPlayer = new Player("Player Name");
    //static Card[] testHand = new Card[]{ new Card(Type.INFANTRY, "Iceland"), new Card(Type.CAVALRY, "Japan"),
    //        new Card(Type.WILD, "Peru")};    
    
    public PlayerTest() {
        
    }
    
    @Test
    public void update() {
        System.out.println("update");
        
        Player testPlayer = new Player("Player Name");        
        String China = "China";
        
        testPlayer.update(true, China);        
    }
    
    @Test
    public void getCardCount() {
        System.out.println("getCardCount");
        
        Player testPlayer = new Player("Player Name");        
        testPlayer.addCards(new Card(Type.ARTILLERY, "China"));
        
        final int expected = 1;        
        final int actual = testPlayer.getCardCount();
        
        assertEquals(actual, expected);        
    }
    
    @Test
    public void territoriesConqueredThisTurn() {
        System.out.println("territoriesConqueredThisTurn");
        
        Player testPlayer = new Player("Player Name");                
        
        final int expected = 0;        
        final int actual = testPlayer.territoriesConquered();
        
        assertEquals(actual, expected);       
    }        
    
    @Test
    public void getActive() {
        System.out.println("getActive");

        Player testPlayer = new Player("Player Name");          
        
        final boolean expected = true;        
        final boolean actual = testPlayer.getActive();

        assertEquals(actual, expected);
    }
    
    @Test
    public void setActive() {
        System.out.println("setActive");

        Player testPlayer = new Player("Player Name");          
        
        final boolean expected = false;
        testPlayer.setActive(false);
        
        final boolean actual = testPlayer.getActive();

        assertEquals(actual, expected);
    }    
    
    @Test
    public void getNumTerritoriesClaimed() {
        System.out.println("getNumTerritoriesClaimed");

        Player testPlayer = new Player("Player Name");          
        
        final int expected = 0;        
        
        final int actual = testPlayer.getNumTerritoriesClaimed();

        assertEquals(actual, expected);
    }
    
    @Test
    public void updateTerritoriesClaimed() {
        System.out.println("updateTerritoriesClaimed");

        Player testPlayer = new Player("Player Name");          
        
        final int expected = 5;        
        testPlayer.updateTerritoriesClaimed(5);
        
        final int actual = testPlayer.getNumTerritoriesClaimed();

        assertEquals(actual, expected);
    }
    
    @Test
    public void getTerritoryList() {
       System.out.println("getTerritoryList");

       Player testPlayer = new Player("Player Name");          
        
       final TerritoryList expected = new TerritoryList();                     
       testPlayer.setTerritoryList(expected);
      
       final TerritoryList actual = testPlayer.getTerritoryList();

       assertEquals(actual, expected);
    }
    
    @Test
    public void setTerritoryList() {
       System.out.println("setTerritoryList");

       Player testPlayer = new Player("Player Name");          
        
       final TerritoryList expected = null;        
              
       testPlayer.setTerritoryList(null);
       
       final TerritoryList actual = testPlayer.getTerritoryList();

       assertEquals(actual, expected);
    } 
         
    @Test
    public void printOwnedTerritories() {    
       System.out.println("printOwnedTerritories");

       Player testPlayer = new Player("Player Name");          
        
       testPlayer.printOwnedTerritories();
    }

    @Test
    public void getPlayerName() {
       System.out.println("getPlayerName");

       Player testPlayer = new Player("Player Name");          
        
       final TerritoryList expected = null;                      
       testPlayer.setTerritoryList(null);
       
       final TerritoryList actual = testPlayer.getTerritoryList();

       assertEquals(actual, expected);
    }
    
    public void testGetPlaceableInfantry() {
        System.out.println("testGetPlaceableInfantry");

        Player testPlayer = new Player("Player Name");          
        
        final int expected = 5;                      
        testPlayer.updatePlaceableInfantry(expected);
       
        final int actual = testPlayer.getPlaceableInfantry();
        
        assertEquals(actual, expected);        
    }
    
    @Test
    public void TestUpdatePlaceableInfantry() {
        
        System.out.println("TestUpdatePlaceableInfantry");
        
        // Arrange
        final int expected = 4;
        Player testPlayer = new Player("Player Name");   
        
        // Act             
        testPlayer.updatePlaceableInfantry(4);
        final int actual = testPlayer.getPlaceableInfantry();

        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void viewHand() {
        Card China = new Card(Type.CAVALRY, "China");
        Card Alberta = new Card(Type.CAVALRY, "Alberta");
        Card Irkutsk = new Card(Type.CAVALRY, "Irkutsk");
        
        Player testPlayer = new Player("Player Name");   
        
        testPlayer.addCards(China);
        testPlayer.addCards(Alberta);
        testPlayer.addCards(Irkutsk);    
        
        testPlayer.viewHand();
    }
    
    @Test 
    public void updateCardValue() {
        Player testPlayer = new Player("Player Name");   
        
        for (int i = 0; i < 7;i++) {
            testPlayer.updateCardValue();
        }        
    }
        
    @Test
    public void calculateInfantry() {
       
        System.out.println("calculateInfantry");        
        
        // Arrange
        final int expected = 3;
        Player testPlayer = new Player("Player Name");   
        
        Territory Afghanistan = new Territory(TerritoryName.AFGHANISTAN, Continent.ASIA,
                new String[]{"China","India","Middle East","Ukraine","Ural"});
        Territory China = new Territory(TerritoryName.CHINA, Continent.ASIA,
                new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"});        
        
        // Act
        Deck deck = new Deck();
        deck.addCards(new Card(Type.CAVALRY, "Afghanistan"));
        deck.addCards(new Card(Type.INFANTRY, "China"));
        testPlayer.claimTerritory(Afghanistan);
        testPlayer.claimTerritory(China);
        final int actual = testPlayer.calculateInfantry(deck);

        // Assert
        Assert.assertEquals(actual, expected);       
    }

    @Test
    public void validPlay() {
        
        System.out.println("validPlay");   
        
        // Arrange
        final boolean expectedTrue = true;
        final boolean expectedFalse = false;
        
        Card China = new Card(Type.CAVALRY, "China");
        Card Alberta = new Card(Type.CAVALRY, "Alberta");
        Card Irkutsk = new Card(Type.CAVALRY, "Irkutsk");
        Card Venezuela = new Card(Type.INFANTRY, "Venezuela");
        Card Japan = new Card(Type.ARTILLERY, "Japan");
        Card Wild = new Card(Type.WILD, "");
                
        Player testPlayer = new Player("Player Name");
        
        // Act
        final boolean sameTypes = testPlayer.validPlay(China, Alberta, Irkutsk);
        final boolean differentTypes = testPlayer.validPlay(China, Venezuela, Japan);
        final boolean wildTypes = testPlayer.validPlay(China, Alberta, Wild);
        final boolean invalidTypes = testPlayer.validPlay(China, Venezuela, Irkutsk);
        
        // Assert        
        Assert.assertEquals(sameTypes, expectedTrue);    // Same Types
        Assert.assertEquals(differentTypes, expectedTrue);    // Different Types
        Assert.assertEquals(wildTypes, expectedTrue);       // Wild type
        Assert.assertEquals(invalidTypes, expectedFalse); // Expected false
    }
    
    @Test
    public void addCards() {
        
        System.out.println("addCards");
        
        // Arrange
        final int expected = 1;       
        Player testPlayer = new Player("Player Name");          
        Card China = new Card(Type.CAVALRY, "China");
        
        // Act
        testPlayer.addCards(China);
        final int actual = testPlayer.getCardCount();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }
    
     @Test
    public void useCards() {
        
        System.out.println("useCards");
        
        ByteArrayInputStream in;
                
        Player testPlayer = new Player("Player Name");
        
        Card China = new Card(Type.CAVALRY, "China");
        Card Alberta = new Card(Type.CAVALRY, "Alberta");
        Card Irkutsk = new Card(Type.CAVALRY, "Irkutsk");
        Territory tChina = new Territory(TerritoryName.CHINA, Continent.ASIA,
                new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"});       
        
        testPlayer.addCards(China);
        testPlayer.addCards(Alberta);
        testPlayer.addCards(Irkutsk);

        Main.territories.put("China", tChina);
        testPlayer.claimTerritory(tChina);
                
        int cardIndex[] = {0,1,2};
        
        in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        
        testPlayer.useCards(cardIndex); 
    }
                
    // TODO
    @Test
    public void playHand() {
        
        System.out.println("playHand");
        
        // Arrange
        ByteArrayInputStream in;
        final int expectedInvalid = 0;
        final int expectedValid = 4;
        
        Card China = new Card(Type.CAVALRY, "China");
        Card Alberta = new Card(Type.CAVALRY, "Alberta");
        Card Irkutsk = new Card(Type.CAVALRY, "Irkutsk");
                       
        Player testPlayer = new Player("Player Name");  
                        
        // Act
        Deck deck = new Deck();
        
        deck.addCards(China);
        deck.addCards(Alberta);
        deck.addCards(Irkutsk);
               
        testPlayer.addCards(China);
        testPlayer.addCards(Alberta);                    
        testPlayer.addCards(Irkutsk);
               
        Main.territories.put("China", new Territory(TerritoryName.CHINA, Continent.ASIA,
                new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"}));
        Main.territories.put("Alberta", new Territory(TerritoryName.ALBERTA, Continent.NORTH_AMERICA,
                new String[]{"Alaska","North West Territory","Ontario","Western United States"}));
        Main.territories.put("Irkutsk", new Territory(TerritoryName.IRKUTSK, Continent.ASIA,
                new String[]{"Kamchatka","Mongolia","Siberia","Yakutsk"}));
        //final int actualInvalid = testPlayer.playHand(deck);
        
        in = new ByteArrayInputStream("0\n1\n2".getBytes());
        System.setIn(in);
        testPlayer.playHand(deck);                                        
    }   
    
    @Test    
    public void TestPlaceInfantry() {   
        
        System.out.println("TestPlaceInfantry");
        
        // Arrange
        Player testPlayer = new Player("Player Name");
        Territory testTerritory = new Territory(TerritoryName.JAPAN,Continent.ASIA, 7,
                new Player("Test Player"), new String[]{"Adjacent Territory"});                
        
        final int expected = testTerritory.getNumArmies() + 3;
        
        // Act                 
        Main.territories.put("Japan", testTerritory);        
        testPlayer.placeInfantry("Japan", 3);        
        final int actual = testTerritory.getNumArmies();

        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void claimTerritory() {
        
        System.out.println("claimTerritory");
        
        // Arrange
        Player testPlayer = new Player("Player Name");
        Territory testTerritory = new Territory(TerritoryName.JAPAN,Continent.ASIA, 7,
                new Player("Test Player"), new String[]{"Adjacent Territory"});   
        
        final Player expected = testPlayer;
        
        // Act
        testPlayer.claimTerritory(testTerritory);
        final Player actual = testTerritory.getOwner();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void loseTerritory() {
        
        System.out.println("loseTerritory");
        
        // Arrange        
        Player testPlayer = new Player("Player Name");
        Territory testTerritory = new Territory(TerritoryName.JAPAN,Continent.ASIA, 7,
                new Player("Test Player"), new String[]{"Adjacent Territory"}); 
        
        final Player expected = testPlayer;        
        // Act
        testPlayer.claimTerritory(testTerritory);
        testPlayer.loseTerritory(testTerritory);
        final Player actual = testTerritory.getOwner();
        
        // Assert
        Assert.assertNotEquals(actual, expected);
    }

    @Test
    public void moveInArmies() {         
        
        System.out.println("moveInArmies");
        
        Player testPlayer = new Player("Player Name");
        Territory China = new Territory(TerritoryName.CHINA, Continent.ASIA,
                new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"});
        Territory India = new Territory(TerritoryName.INDIA, Continent.ASIA,
                new String[]{"Afghanistan","China","Middle East","Siam"});
        
        China.incrementArmies(15);
        India.incrementArmies(1);
        
        final int expected = 10;        
        
        ByteArrayInputStream in;        
        in = new ByteArrayInputStream("10".getBytes());
        System.setIn(in);                
        final int actual = testPlayer.moveInArmies(China, India);
        
        assertEquals(actual, expected);        
    }
    
    @Test 
    public void drawCards() {       
        
        System.out.println("drawCards");
        
        Player testPlayer = new Player("Player Name");
        
        Deck deck = new Deck();
        deck.addCards(new Card(Type.ARTILLERY, "China"));
        testPlayer.updateTerritoriesClaimed(1);
        testPlayer.drawCards(deck);        
    }
    
    //TODO
    @Test
    public void attack() {      
        
        System.out.println("attack");        
        
        Player attacker = new Player("attacker");
        Player defender = new Player("defender");
        ByteArrayInputStream in; 
        
        Territory China = new Territory(TerritoryName.CHINA, Continent.ASIA,
                new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"});
        Territory India = new Territory(TerritoryName.INDIA, Continent.ASIA,
                new String[]{"Afghanistan","China","Middle East","Siam"});
        
        Main.territories.put("China", China);
        Main.territories.put("India", India);
        
        China.incrementArmies(10);
        India.incrementArmies(4);
        
        attacker.claimTerritory(China);
        defender.claimTerritory(India);               
        
        in = new ByteArrayInputStream("3\n1".getBytes());  // using 3 troops to attack
        System.setIn(in);    
        attacker.attack(China, India);        
    }
} 
    