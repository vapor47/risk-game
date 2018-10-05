import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    static Territory testTerritory = new Territory("Name", "Continent", new String[]{"adjacentA", "adjacentB}"});
    static Player testPlayer = new Player("Player Name");
    static Card[] testHand = new Card[]{ new Card(Type.INFANTRY, "Iceland"), new Card(Type.CAVALRY, "Japan"),
            new Card(Type.WILD, "Peru")};


    @Test
    public void TestUpdatePlaceableInfantry() {
        final int expected = 4;
        testPlayer.updatePlaceableInfantry(4);

        Assert.assertEquals(testPlayer.getPlaceableInfantry(), expected);
    }

    @Test
    public void calculateInfantry() {
    }

    @Test
    public void validPlay() {
    }

    @Test
    public void playHand() {
    }

    @Test
    public void TestPlaceInfantry() {
        final int expected = testTerritory.getNumArmies() + 3;
        testPlayer.placeInfantry("name", 3);

        Assert.assertEquals(testTerritory.getNumArmies(), expected);
    }

    @Test
    public void claimTerritory() {
        testPlayer.claimTerritory(testTerritory);

        Assert.assertEquals(testTerritory.getOwner(), testPlayer);
    }

    @Test
    public void loseTerritory() {
        testPlayer.claimTerritory(testTerritory);
        testPlayer.loseTerritory(testTerritory);

        Assert.assertNotEquals(testTerritory.getOwner(), testPlayer);
    }


    @Test
    public void attack() {
    }
}