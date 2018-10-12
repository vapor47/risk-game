public class FortifyingTerritoriesCommand extends UndoableCommand {
    
    private Player player;   
    private String territory;
    private int troops;
    
    public FortifyingTerritoriesCommand(Player player, String territory, int troops) {        
        this.player = player;
        this.territory = territory;
        this.troops = troops;       
    }
    
    @Override
    public void execute() {
        player.placeInfantry(territory, troops); 
    }
    
    @Override
    public void undo() {
        Territory curTerritory = Risk_Game.territories.get(territory);
        curTerritory.decrementArmies(troops);   //Undo action of placing troops
        player.updatePlaceableInfantry(troops); //Returns troops to player                
    }
}
