public class AttackCommand extends UndoableCommand {
    
    private Territory attackingTerritory;
    private Territory defendingTerritory;
    private int attackerPreviouTroops;
    private int defenderPreviouTroops;
    private Player prevOwner;
    private Player attacker;

    public AttackCommand(Territory attacker, Territory defender) {
        attackingTerritory = attacker;
        defendingTerritory = defender;
        
        attackerPreviouTroops = attackingTerritory.getNumArmies();
        defenderPreviouTroops = defendingTerritory.getNumArmies();
        
        this.attacker = attackingTerritory.getOwner();
        this.prevOwner = defendingTerritory.getOwner();
    }
    
    @Override
    public void execute() {
        attacker.attack(attackingTerritory, defendingTerritory);
    }
    
    
    @Override
    public void undo() {
        if (prevOwner.getActive() == false) {
            prevOwner.setActive(true);
//            Main.playerList.add(prevOwner.getIndex(), prevOwner.getPlayerName());  //Adds player back into list if he ded
            Main.playerMapTest.put(prevOwner.getPlayerName(), prevOwner);  //Adds player back into list if he ded
        }
        
        attackingTerritory.decrementArmies(attackingTerritory.getNumArmies());
        attackingTerritory.incrementArmies(attackerPreviouTroops);
        
        defendingTerritory.decrementArmies(defendingTerritory.getNumArmies());
        defendingTerritory.incrementArmies(defenderPreviouTroops);
        
        defendingTerritory.setOwner(prevOwner);
        
        System.out.printf("Troops on %s: %d\n", attackingTerritory.getTerritoryName(), attackingTerritory.getNumArmies());     
        System.out.printf("Troops on %s: %d\n", defendingTerritory.getTerritoryName(), defendingTerritory.getNumArmies());
        
        if (attacker.getNumTerritoriesClaimed() > 0) {
            attacker.updateTerritoriesClaimed(-1);
        } 
    }
}
