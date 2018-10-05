import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TerritoryTest {

    @Test
    public void incrementArmies() {
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
    }

    @Test
    public void getTerritoryName() {
        Territory test = new Territory("Test Name","Test Continent", 3,
                new Player("Test Player"), new String[]{"Adjacent Territory"});

        Assert.assertEquals(test.getTerritoryName(), "Test Name");
    }

    @Test
    public void getContinent() {
        Territory test = new Territory("Test Name","Test Continent", 3,
                new Player("Test Player"), new String[]{"Adjacent Territory"});

        Assert.assertEquals(test.getContinent(), "Test Continent");
    }

    @Test
    public void getNumArmies() {
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
        Player expected = new Player("Test Player");
        Territory test = new Territory(TerritoryName.WESTERN_AUSTRALIA,Continent.AUSTRALIA, 7,
                expected, new String[]{"Adjacent Territory"});

        final Player actual = test.getOwner();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getAdjacentTerritories() {
    }
}