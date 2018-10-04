import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TerritoryTest {

    @Test
    public void incrementArmies() {
        // Arrange
        final int expected = 4;
        Territory test = new Territory("Test Name","Test Continent", new String[]{"Adjacent Territory"});

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
        Territory test = new Territory("Test Name","Test Continent", 7,
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
    }

    @Test
    public void getContinent() {
    }

    @Test
    public void getNumArmies() {
        // Arrange
        final int expected = 3;
        Territory test = new Territory("Test Name","Test Continent", 3,
                new Player("Test Player"), new String[]{"Adjacent Territory"});

        // Act
        final int actual = test.getNumArmies();

        // Assert
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getOwner() {
        Player expected = new Player("Test Player");
        Territory test = new Territory("Test Name","Test Continent", 7,
                expected, new String[]{"Adjacent Territory"});

        final Player actual = test.getOwner();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getAdjacentTerritories() {
    }

    @Test
    public void printAdjacentTerritories() {
    }

    @Test
    public void listTerritoryInfo() {
    }
}