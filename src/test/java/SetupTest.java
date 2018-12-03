import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class SetupTest {

    @Test
    public void GetInstanceShouldReturnNotNull() {
        Assert.assertNotEquals(null, Setup.getInstance());
    }

    @Test
    public void telegramSetup() {
        ByteArrayInputStream in = new ByteArrayInputStream("y".getBytes());

        Setup.getInstance().telegramSetup(in);

        Assert.assertEquals(3, Setup.getInstance().numPlayers);
    }

    @Test
    public void PlayerIsUsingTelegram() {
        ByteArrayInputStream in = new ByteArrayInputStream("y".getBytes());

        final boolean actual = Setup.getInstance().isUsingTelegram(in);
        final boolean expected = true;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void PromptNumPlayersShouldReturnCorrectResponse() {
        ByteArrayInputStream in = new ByteArrayInputStream("4".getBytes());

        final int actual = Setup.getInstance().promptNumPlayers(in);
        final int expected = 4;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void twoPlayerStart() {
    }

    @Test
    public void normalStart() {
    }

    @Test
    public void listUnclaimedTerritories() {
    }

    @Test
    public void TwoPlayerGameShouldIncludeNeutral() {
        Setup.getInstance().createPlayers(2);
        Assert.assertTrue(Main.playerMapTest.containsKey("Neutral"));
    }

    @Test
    public void TwoPlayersShouldHave40ArmiesEach() {
        final int actual = Setup.getInstance().setInitialArmies(2);
        final int expected = 40;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void ThreePlayersShouldHave35ArmiesEach() {
        final int actual = Setup.getInstance().setInitialArmies(3);
        final int expected = 35;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FourPlayersShouldHave30ArmiesEach() {
        final int actual = Setup.getInstance().setInitialArmies(4);
        final int expected = 30;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void FivePlayersShouldHave25ArmiesEach() {
        final int actual = Setup.getInstance().setInitialArmies(5);
        final int expected = 25;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void SixPlayersShouldHave20ArmiesEach() {
        final int actual = Setup.getInstance().setInitialArmies(6);
        final int expected = 20;

        Assert.assertEquals(expected, actual);
    }
}