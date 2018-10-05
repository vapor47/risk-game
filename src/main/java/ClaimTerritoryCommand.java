public class ClaimTerritoryCommand extends UndoableCommand {
    
    private Player player;    
    private Player prevOwner;
    private Territory territory;
    private TerritoryList previousTerritoryList;
    
    public ClaimTerritoryCommand(Player player, Territory territory) {        
        this.player = player;
        this.territory = territory;
        prevOwner = territory.getOwner();
        previousTerritoryList = player.getTerritoryList();
    }
    
    @Override
    public void execute() {
        player.claimTerritory(territory);
    }
    
    @Override
    public void undo() {
        player.setTerritoryList(previousTerritoryList);
        territory.setOwner(prevOwner);
        
        player.updatePlaceableInfantry(1);                      
        territory.decrementArmies(1);
    }
}
