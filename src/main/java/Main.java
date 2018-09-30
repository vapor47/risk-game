
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

    static Deck deck = new Deck();
    
    public static void main(String[] args){        
        Scanner sc = new Scanner(System.in);        
        Setup setup = new Setup();
        int playerIndex = setup.getStartingPlayerIndex();
        boolean isPlaying = true;        
        String territoryName;
        
        Player currentPlayer = playerMap.get(playerList.get(playerIndex));
        
        while(isPlaying){ //until only 1 player occupies territories(except for neutral in 2 player games)
            //-------------------------------CALCULATE ARMIES & PLACING INFANTRY----------------------------//            
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
            
            //------------------------------------------ATTACK--------------------------------------//
            System.out.print("\nWould you like to attack this turn?: (y/n)");
            territoryName = sc.nextLine();
            if (territoryName.equalsIgnoreCase("y")) {  
                System.out.println();                
                //Prints out Player's territory and the num of troops on them        
                do {
                    System.out.println("Your Territories:");
                    for(Map.Entry<String, Territory> x: territories.entrySet()){  
                        if (x.getValue().getOwner() == currentPlayer) {
                            System.out.print(x.getValue().getTerritoryName() + " || ");
                            System.out.println(x.getValue().getNumArmies());
                        }                            
                    }
                    //Attack Prompt
                    System.out.println("\nChoose:\n\t1) A territory to attack from\n\t2) An adjacent territory to attack\n"); 
                    System.out.print("- Type in name of territory to display adjacent territories.\n- Press \"Enter\" to continue with attack\n");                    
                    territoryName = sc.nextLine(); 
                    
                    if(territoryName.isEmpty()){
                        break;
                    }
                                                                   
                    if (territories.containsKey(territoryName)) {                        
                        Territory currentTerritory = territories.get(territoryName);
                        System.out.println("\nAdjacent Territories: ");
                        currentTerritory.printAdjacentTerritories();                                           
                        System.out.println("\n");                        
                        //TODO: Allow player to see adjacent territory info                        
                    } 
                    else if (!territories.containsKey(territoryName) && !territoryName.isEmpty()) {
                        System.out.println("Enter in a valid territory: ");
                    } 
                    else {
                        
                    }                   
                } while (!(territoryName.isEmpty()));
                
                //Checking if territory attacking from is valid
                String attackingTerritory;
                boolean validTerritoryFrom = false;                
                do {                    
                    System.out.print("- Choose territory to attack from: ");
                    attackingTerritory = sc.nextLine();
                    
                    //Checks if player types "list-owned"
                    if(attackingTerritory.equals("list-owned")) {
                        System.out.println("\nYour Territories:");
                        playerMap.get(currentPlayer.getName()).printOwnedTerritories();
                        System.out.println();
                        continue;                                
                    }
                    
                    //Checks if territory is valid
                    if (territories.containsKey(attackingTerritory)) {
                        //checks to see if player is owner of the territory              
                        if(playerMap.get(currentPlayer.getName()) == territories.get(attackingTerritory).getOwner()) {                            
                            validTerritoryFrom = true;
                        } 
                        else {
                            System.out.println("Cannot select enemy territory\n" +
                                "For a list of owned territories, type 'list-owned'\n");
                        }
                    }
                    else { //Territory not valid
                        System.out.println("Please choose a valid territory\n" +
                                "For a list of owned territories, type 'list-owned'\n");
                    }                                   
                } while(!validTerritoryFrom);
                
                //Checking if territory attacking is valid
                String defendingTerritory;
                boolean validTerritoryTo = false;
                do {
                    System.out.print("- Choose territory to attack: ");
                    defendingTerritory = sc.nextLine();
                    
                    //Checks if player types "adjacent"
                    if(defendingTerritory.equals("adjacent")) {
                        System.out.println("\nAdjacent territories to attack: ");
                        territories.get(attackingTerritory).printAdjacentTerritories();
                        System.out.println("\n");
                        continue;                                
                    }
                                 
                    //Checks if territory is valid
                    if (territories.containsKey(defendingTerritory)) {
                        //Checks if defending territory is adjacent to attacking territory
                        for (String adjTerritory : territories.get(attackingTerritory).getAdjacentTerritories()) {
                            if (defendingTerritory.equals(adjTerritory)) {
                                validTerritoryTo = true;
                                break;
                            }                                                                                   
                        }
                        if (!validTerritoryTo) {
                            System.out.println("Territory is not adjacent\n" +
                                "For a list of adjacent territories, type 'adjacent'\n");
                        }
                    }                                                             
                    else { //Territory not valid
                        System.out.println("Please choose a valid territory\n" +
                                "For a list of adjacent territories, type 'adjacent'\n");
                    }                                   
                } while(!validTerritoryTo);                
                System.out.println();
                
                currentPlayer.attack(territories.get(attackingTerritory), territories.get(defendingTerritory));
                //TODO: Continue with attack phase                                    
                
            }// End if for attacking
 
            //TODO: End attack phase, fortify            
            playerIndex--;
            if (playerIndex == -1) {
                playerIndex = playerList.size() - 1;
            }                
            
            currentPlayer = playerMap.get(playerList.get(playerIndex));   
            //isPlaying will be false when playerList == 1   
        } //End while
                     
    } 
}