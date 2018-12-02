import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    //static Territory testTerritory = new Territory(TerritoryName.valueOf("Name"), Continent.valueOf("Continent"), new String[]{"adjacentA", "adjacentB}"});
    //static Player testPlayer = new Player("Player Name");
    //static Card[] testHand = new Card[]{ new Card(Type.INFANTRY, "Iceland"), new Card(Type.CAVALRY, "Japan"),
    //        new Card(Type.WILD, "Peru")};    
    
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
        testPlayer.claimTerritory(Afghanistan);
        testPlayer.claimTerritory(China);
        final int actual = testPlayer.calculateInfantry();

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
        
    // TODO
    @Test
    public void playHand() {
        
        System.out.println("playHand");
        
        // Arrange
        final int expectedInvalid = 0;
        final int expectedValid = 4;
        
        Card China = new Card(Type.CAVALRY, "China");
        Card Alberta = new Card(Type.CAVALRY, "Alberta");
        Card Irkutsk = new Card(Type.CAVALRY, "Irkutsk");
                       
        Player testPlayer = new Player("Player Name");  
        
        // Act
        testPlayer.addCards(China);
        testPlayer.addCards(Alberta);                              
        final int actualInvalid = testPlayer.playHand();
        
        // Assert
        Assert.assertEquals(actualInvalid, expectedInvalid);    // Player has less than 3 cards
        
        //testPlayer.addCards(Irkutsk);
        
        //testPlayer.playHand();
                
        /* Test for:
        1) player has less than 3 cards
        2) player has >= 3 cards
            - Player plays valid cards
            - Player enters 0
        */                           
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

    //TODO
    @Test
    public void attack() {
        
    }
}