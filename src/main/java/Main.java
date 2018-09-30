
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    // Key = Player name; Value = Player object
    // Does not include neutral in 2 player games    
    static Map<String, Player> playerMap = new HashMap<String, Player>();

    // Holds Player names and maintains turn order
    static ArrayList<String> playerList = new ArrayList<String>();
    
    static Map<String,Territory> territories = new HashMap();

    public static void main(String[] args){        
        Scanner sc = new Scanner(System.in);        
        Setup setup = new Setup();
        int playerIndex = setup.getStartingPlayerIndex();
        boolean isPlaying = true;        
        String territoryName;
        
        Player currentPlayer = playerMap.get(playerList.get(playerIndex));
        
        while(isPlaying){ //until only 1 player occupies territories(except for neutral in 2 player games)
            //----------------CALCULATE ARMIES & PLACING INFANTRY------------------//            
            System.out.printf("\nIt is Player %d's turn: \n", playerIndex + 1);
            currentPlayer.playHand();
            currentPlayer.calculateInfantry();            
            
            if (currentPlayer.getPlaceableInfantry() > 0) {       
                System.out.printf("- Place your remaining armies on your territories\n", currentPlayer.getPlaceableInfantry());
                while (currentPlayer.getPlaceableInfantry() > 0) {
                    System.out.println("- Remaining armies: " + currentPlayer.getPlaceableInfantry());
                    System.out.println("- Choose which territory you would like to fortify: \n");                    
                    for(Map.Entry<String, Territory> x: territories.entrySet()){  //Prints out Player's territory and the num of troops on them
                        if (x.getValue().getOwner() == currentPlayer) {
                            System.out.print(x.getValue().getTerritoryName() + " || ");
                            System.out.println(x.getValue().getNumArmies());
                        }                            
                    }   
                                        
                    territoryName = sc.nextLine();                    
                    while (!territories.containsKey(territoryName)) {
                        System.out.println("Enter in a valid territory");
                        territoryName = sc.nextLine();
                    }                                        
                                                 
                    if (territories.containsKey(territoryName)) {                            
                        System.out.printf("How many troops would you like to move to %s? ", territoryName);
                        int troopsToMove = sc.nextInt();
                        sc.nextLine();
                            
                        while (troopsToMove > currentPlayer.getPlaceableInfantry() || troopsToMove < 0) {
                            System.out.println("Invalid number of troops to move.");
                            System.out.printf("How many troops would you like to move to %s? ", territoryName);
                            troopsToMove = sc.nextInt();
                            sc.nextLine();
                        }                          
                            
                        territories.get(territoryName).incrementArmies(troopsToMove);
                        System.out.printf("- Added %d armies to %s\n", troopsToMove, territoryName);
                    }
                }
            }
            
            //----------------------ATTACK---------------------//
            System.out.print("\nWould you like to attack this turn?: (y/n)");
            territoryName = sc.nextLine();
            if (territoryName.equalsIgnoreCase("y")) {  
                System.out.println();                
                //Prints out Player's territory and the num of troops on them
                for(Map.Entry<String, Territory> x: territories.entrySet()){  
                        if (x.getValue().getOwner() == currentPlayer) {
                            System.out.print(x.getValue().getTerritoryName() + " || ");
                            System.out.println(x.getValue().getNumArmies());
                        }                            
                }
                
                //Attack Prompt
                do {
                    System.out.println("\nChoose:\n\t1) A territory to attack from\n\t2) An adjacent territory to attack\n"); 
                    System.out.print("- Type in name of territory to display adjacent territories.\n- Press \"Enter\" to continue with attack\n");
                    territoryName = sc.nextLine();                    
                    if (territories.containsKey(territoryName)) {
                        Territory currentTerritory = territories.get(territoryName);
                        currentTerritory.printAdjacentTerritories();                                           
                        System.out.println();                        
                        //TODO: Allow player to see adjacent territory info                        
                    } else if (!territories.containsKey(territoryName) && !territoryName.isEmpty()) {
                        System.out.println("Enter in a valid territory: ");
                    } else {
                        
                    }                   
                } while (!(territoryName.isEmpty()));
                
                
                //TODO: Continue with attack phase
                //currentPlayer.attack();
            }// End if
 
            //TODO: End attack phase, fortify            
            playerIndex--;
            if (playerIndex == (setup.getNumPlayers() - 1)) {
                playerIndex = 0;
            }                
            
            currentPlayer = playerMap.get(playerList.get(playerIndex));   
            //isPlaying will be false when playerList == 1   
        } //End while
                     
    } 
}