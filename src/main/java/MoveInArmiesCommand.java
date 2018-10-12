public class MoveInArmiesCommand extends UndoableCommand {
    
    private Player curPlayer;
    private Territory from;
    private Territory to;
    int numTroopsMoved;
    
    public MoveInArmiesCommand(Territory from, Territory to) {
        curPlayer = from.getOwner();
        this.from = from;
        this.to = to;              
    }
    
    @Override
    public void execute() {
        numTroopsMoved = curPlayer.moveInArmies(from, to);  //Returns number of troop moved
        System.out.printf("Moved %d troops from %s to %s\n", numTroopsMoved, from.getTerritoryName(), to.getTerritoryName());
    }
    
    @Override
    public void undo() {
        from.incrementArmies(numTroopsMoved);
        to.decrementArmies(numTroopsMoved);
    }
}
