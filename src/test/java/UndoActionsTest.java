import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UndoActionsTest {
    
    CommandManager commandManager = new CommandManager();
    
    public UndoActionsTest() {        
    }
    
    /**
     * Test of undoAttack method, of class Deck.
    */
    @Test
    public void undoPlacingInfantry() {
        System.out.println("UndoPlacingInfantry");
        // Create Player & a territory he owns
        Territory China = new Territory(TerritoryName.CHINA, Continent.ASIA, new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"});
        Player instance = new Player("Instance");        
    }
               
    /**
     * Test of undoAttack method, of class Deck.
    */
    @Test
    public void undoAttack() {
        System.out.println("UndoAttack");
        //  create 2 players, attacker, defender
        //  attacker = 10 troops
        //  defender = 8 troops
        //  call attack function attack -> defender
        //  display who won as well as new troops on each territory
        //  call undo 
        //  display old troops on territory         
        Territory China = new Territory(TerritoryName.CHINA, Continent.ASIA, new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"});
        Territory India = new Territory(TerritoryName.INDIA, Continent.ASIA, new String[]{"Afghanistan","China","Middle East","Siam"});
        
        Player attacker = new Player("Attacker");
        Player defender = new Player("Defender");
        
        China.setOwner(attacker);
        India.setOwner(defender);
        
        China.incrementArmies(10);
        India.incrementArmies(8);                
        
        //attacker.attack(China, India);     
        
        
        
        //AttackCommand instance = new AttackCommand(China, );
        /*
        Player attacker = new Player("Attacker");
        Player defender = new Player("Defender");
        
        //attacker.updatePlaceableInfantry(10);
        //defender.updatePlaceableInfantry(8); 
                
        //Territory China = new Territory(TerritoryName.CHINA, Continent.ASIA, new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"});
        //Territory India = new Territory(TerritoryName.INDIA, Continent.ASIA, new String[]{"Afghanistan","China","Middle East","Siam"});
        
        attacker.claimTerritory(Main.territories.get("China"));
        defender.claimTerritory(Main.territories.get("India"));
        
        attacker.placeInfantry("China", 10);
        defender.placeInfantry("India", 8);
        
        //attacker.attack(China, India);        
        */
    }
    
    
    
    
}