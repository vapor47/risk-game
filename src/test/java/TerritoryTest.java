import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TerritoryTest {

    @Test
    public void incrementArmies() {
        
        System.out.println("incrementArmies");        
        
        // Arrange
        final int expected = 4;
        Territory test = new Territory(TerritoryName.SIAM,Continent.ASIA, new String[]{"Adjacent Territory"});

        // Act
        test.incrementArmies(4);
        final int actual = test.getNumArmies();

        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void decrementArmies() {
        
        System.out.println("decrementArmies");        
        
        // Arrange
        final int expected = 5;
        Territory test = new Territory(TerritoryName.JAPAN,Continent.ASIA, 7,
                new Player("Test Player"), new String[]{"Adjacent Territory"});

        // Act
        test.decrementArmies(2);
        final int actual = test.getNumArmies();

        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void setOwner() { 
        
        System.out.println("setOwner"); 
        
        // Arrange
        final Player expected = new Player("Test Player");
        Territory test = new Territory(TerritoryName.JAPAN,Continent.ASIA, 7,
                new Player("Test Player"), new String[]{"Adjacent Territory"});
        
        // Act
        test.setOwner(expected);
        final Player actual = test.getOwner();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getTerritoryName() {
        
        System.out.println("getTerritoryName");
        
        // Arrange
        final TerritoryName expected = TerritoryName.JAPAN;
        Territory test = new Territory(TerritoryName.JAPAN,Continent.ASIA, 7,
                new Player("Test Player"), new String[]{"Adjacent Territory"});

        // Act
        final TerritoryName actual = test.getTerritoryName();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getContinent() {
        
        System.out.println("getContinent");
        
        // Arrange        
        final Continent expected = Continent.ASIA;
        Territory test = new Territory(TerritoryName.JAPAN,Continent.ASIA, 7,
                new Player("Test Player"), new String[]{"Adjacent Territory"});

        // Act
        final Continent actual = test.getContinent();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getNumArmies() {
        
        System.out.println("getNumArmies");        
        
        // Arrange
        final int expected = 3;
        Territory test = new Territory(TerritoryName.EASTERN_AUSTRALIA,Continent.AUSTRALIA, 3,
                new Player("Test Player"), new String[]{"Adjacent Territory"});

        // Act
        final int actual = test.getNumArmies();

        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getOwner() {
        
        System.out.println("getOwner");
        
        // Arrange
        final Player expected = new Player("Test Player");
        Territory test = new Territory(TerritoryName.WESTERN_AUSTRALIA,Continent.AUSTRALIA, 7,
                expected, new String[]{"Adjacent Territory"});

        // Act
        final Player actual = test.getOwner();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getAdjacentTerritories() {        
        
        System.out.println("getAdjacentTerritories");        
        
        // Arrange
        Territory test = new Territory(TerritoryName.SIAM,Continent.ASIA, new String[]{"Adjacent Territory 1", "Adjacent Territory 2", "Asjacent Territory 3"});        
        final String[] expected = {"Adjacent Territory 1", "Adjacent Territory 2", "Asjacent Territory 3"};
        
        // Act
        final String[] actual = test.getAdjacentTerritories();
        
        // Assert        
        Assert.assertArrayEquals(actual, expected);
    }
    
    @Test
    public void addObserver() {
        
        System.out.println("addObserver");
        
        // Arrange
        TerritoryObserver obs = new Player("Test Player");
        Territory test = new Territory(TerritoryName.SIAM,Continent.ASIA, new String[]{"Adjacent Territory"});        
        final TerritoryObserver expected = obs;
        
        // Act
        test.addObserver(obs);
        final TerritoryObserver actual = test.getObserver();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }
    
    @Test
    public void removeObserver() {
        
        System.out.println("removeObserver");
        
        // Arrange
        Territory test = new Territory(TerritoryName.SIAM,Continent.ASIA, new String[]{"Adjacent Territory"});        
        final TerritoryObserver expected = null;
        
        // Act
        test.removeObserver();
        final TerritoryObserver actual = test.getObserver();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }
    
    @Test
    public void updateUnderAttackStatus() {
                
        System.out.println("updateUnderAttackStatus");
                        
        // Arrange
        Territory test = new Territory(TerritoryName.SIAM,Continent.ASIA, new String[]{"Adjacent Territory"});   
        final boolean expected = true;
                
        // Act  
        test.updateUnderAttackStatus(true);
        final boolean actual = test.isUnderAttack();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }
    
    @Test 
    public void isUnderAttack() {
        
        System.out.println("isUnderAttack");
        
        // Arrange
        Territory test = new Territory(TerritoryName.SIAM,Continent.ASIA, new String[]{"Adjacent Territory"});   
        final boolean expected = false;
        
        // Act
        test.updateUnderAttackStatus(expected);
        final boolean actual = test.isUnderAttack();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }
    
    @Test
    public void getObserver() {
        
        System.out.println("getObserver");
        
        // Arrange
        Territory test = new Territory(TerritoryName.SIAM,Continent.ASIA, new String[]{"Adjacent Territory"});   
        final TerritoryObserver expected = new Player("Observer");
        
        // Act
        test.addObserver(expected);
        final TerritoryObserver actual = test.getObserver();
        
        // Assert
        Assert.assertEquals(actual, expected);
    }
    
    @Test
    public void notifyObservers() {
        
        System.out.println("notifyObservers");
        
        // Arrange
        Territory test = new Territory(TerritoryName.SIAM,Continent.ASIA, new String[]{"Adjacent Territory"});   
        final TerritoryObserver observer = new Player("Observer");
        
        // Act
        test.addObserver(observer);
        test.updateUnderAttackStatus(true);
        test.notifyObservers();                                
    }
    
    @Test
    public void printAdjacentTerritories() {     
        
        System.out.println("printAdjacentTerritories");
                
        // Arrange
        Territory test = new Territory(TerritoryName.AFGHANISTAN, Continent.ASIA,
                new String[]{"China","India","Middle East","Ukraine","Ural"});        
        
        // Act
        test.printAdjacentTerritories();
    }
    
    @Test
    public void listTerritoryInfo(){   
        
        System.out.println("listTerritoryInfo");        
        
        // Arrange
        Territory test = new Territory(TerritoryName.AFGHANISTAN, Continent.ASIA,
                new String[]{"China","India","Middle East","Ukraine","Ural"});    
        
        // Act
        test.listTerritoryInfo();       
    }
    
    @Test
    public void getTerritoryInfo() {
        
        System.out.println("getTerritoryInfo");        
        
        // Arrange
        Territory test = new Territory(TerritoryName.AFGHANISTAN, Continent.ASIA,
                new String[]{"China","India","Middle East","Ukraine","Ural"});    
        
        // Act
        test.getTerritoryInfo();            
    }
}