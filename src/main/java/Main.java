import java.util.*;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Main {
    // Key = Player name; Value = Player object
    // Does not include neutral in 2 player games    
    static HashMap<String, Player> playerMap = new HashMap<String, Player>();

    // Holds Player names and maintains turn order
    static ArrayList<String> playerList = new ArrayList<String>();
    
    static Map<String,Territory> territories = new HashMap<>();

    static LinkedHashMap<String, Player> playerMapTest = new LinkedHashMap<>();
    
    static Deck deck = new Deck();
    
    static CommandManager commandManager = new CommandManager();

    public static void main(String[] args) throws IOException{

		ApiContextInitializer.init();

		TelegramBotsApi riskBot = new TelegramBotsApi();
		try {
			riskBot.registerBot(TelegramJoinBot.getInstance());

		} catch (TelegramApiException e) {
     	  e.printStackTrace();
		}
    	
    	Replay replay = new Replay();
        Scanner sc = new Scanner(System.in);        
        Setup setup = new Setup();
        //test.setPlayerName(playerMap);
        int playerIndex = setup.getStartingPlayerIndex();
        boolean isPlaying = true;        
        String territoryName;
        String userInput;
        
        Player currentPlayer = playerMap.get(playerList.get(playerIndex));
        
        while(isPlaying){ //until only 1 player occupies territories(except for neutral in 2 player games)
            //------------------------------------------------CALCULATE ARMIES & PLACING INFANTRY--------------------------------------------------------//                        
            formattedMessage("Player " + (playerIndex + 1) + "'s turn");
            replay.update("Player " + playerIndex + "'s turn");
            currentPlayer.updatePlaceableInfantry(currentPlayer.calculateInfantry());                                  
            
            if (currentPlayer.getPlaceableInfantry() > 0) {       
                System.out.printf("- Place your remaining armies on your territories\n", currentPlayer.getPlaceableInfantry());
                while (currentPlayer.getPlaceableInfantry() > 0) {                                                            
                    boolean troopsMoved = false;
                    //loops while Player still has armies to place
                    do {                         
                        System.out.println("- Remaining armies: " + currentPlayer.getPlaceableInfantry());
                        System.out.println("- Choose which territory you would like to fortify: ");                    
                        printOwnedTerritory(currentPlayer);
                        System.out.println();
                        
                        replay.update("- Remaining armies: " + currentPlayer.getPlaceableInfantry());
                        
                        territoryName = sc.nextLine(); 
                        
                        //Check if user types "undo"
                        if (territoryName.equals("undo")) {
                            commandManager.undo();                            
                            continue;
                        }

                        //Checks if territory is valid
                        if (!territories.containsKey(territoryName)) {
                            System.out.println("Enter in a valid territory");
                        } //Valid & player is owner of territory
                        else if (currentPlayer == territories.get(territoryName).getOwner()) {
                            System.out.printf("How many troops would you like to move to %s? ", territoryName);
                            int troopsToMove = sc.nextInt();
                            sc.nextLine();
                            
                            while (troopsToMove > currentPlayer.getPlaceableInfantry() || troopsToMove < 0) {
                                System.out.println("Invalid number of troops to move.");
                                System.out.printf("How many troops would you like to move to %s? ", territoryName);
                                troopsToMove = sc.nextInt();
                                sc.nextLine();
                            }
                                      
                            //Executes command & fortifies the territory                            
                            commandManager.executeCommand(new FortifyingTerritoriesCommand(currentPlayer, territoryName, troopsToMove));                            
                            
                            System.out.printf("\n- Added %d armies to %s", troopsToMove, territoryName);
                            troopsMoved = true;                            
                            replay.update("n- Added " + troopsToMove + " armies to " + territoryName);
                            
                        } //Valid, but player does not own the territory      
                        else {
                            System.out.println("Cannot place troops into enemy territory");
                        }                        
                    } while (!troopsMoved);
                    System.out.println();
                } //end while
            } //end if
            
            replay.upload();
            
            //-------------------------------------------------------------ATTACK------------------------------------------------------------------------//
            formattedMessage("Attacking Phase");
            System.out.print("\nWould you like to attack this turn?: (y/n)");

            do {                
                userInput = sc.nextLine();       

                // Checks if user types "undo"
                while (userInput.equals("undo")) {
                        commandManager.undo();
                        System.out.println("Continue Attacking? (y/n)");
                        userInput = sc.nextLine();
                }

                if (userInput.equalsIgnoreCase("y")) {                    
                    System.out.println();                
                    //Prints out Player's territory and the num of troops on them        
                    //Attack Prompt
                    System.out.println("\nChoose:\n\t1) A territory to attack from\n\t2) An adjacent territory to attack\n"); 
                    System.out.print("- Type in name of territory to display adjacent territories.\n- Press \"Enter\" to continue with attack\n");
                    printOwnedTerritory(currentPlayer);
                    territoryName = sc.nextLine(); 
                    
                    do {                                                                    
                        if(territoryName.isEmpty()){
                            break;
                        }

                        if(territoryName.equals("options")) {
                            System.out.println("\nChoose:\n\t1) A territory to attack from\n\t2) An adjacent territory to attack\n"); 
                            System.out.print("- Type in name of territory to display adjacent territories.\n- Type \"options\" to view options\n- Press \"Enter\" to continue with attack\n");
                            printOwnedTerritory(currentPlayer);                               
                        }

                        //TODO: Display territories available to attack
                        if (territories.containsKey(territoryName)) {                        
                            Territory currentTerritory = territories.get(territoryName);
                            System.out.println("\n|| Adjacent Territories to attack ||");
                            currentTerritory.printAdjacentTerritories();                                           
                            System.out.println("\n");                        
                            //TODO: Allow player to see adjacent territory info                        
                        } 
                        else if (!territories.containsKey(territoryName) && !territoryName.isEmpty()) {
                            System.out.println("Enter in a valid territory: ");
                        } 
                        else {

                        }         

                        territoryName = sc.nextLine();

                    } while (!(territoryName.isEmpty()));

                    //Checking if territory attacking from is valid
                    String attackingTerritory;
                    boolean validTerritoryFrom = false;                
                    do {                    
                        System.out.print("- Choose territory to attack from: ");
                        attackingTerritory = sc.nextLine();

                        //Checks if player types "list-owned"
                        if(attackingTerritory.equals("list-owned")) {                            
                            playerMap.get(currentPlayer.getPlayerName()).printOwnedTerritories();
                            System.out.println();
                            continue;                                
                        }

                        //Checks if territory is valid
                        if (territories.containsKey(attackingTerritory)) {
                            //checks to see if player is owner of the territory              
                            if(playerMap.get(currentPlayer.getPlayerName()) == territories.get(attackingTerritory).getOwner()) {                            
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
                        if(defendingTerritory.equals("list-adjacent")) {
                            System.out.println("\n|| Adjacent territories to attack ||");
                            territories.get(attackingTerritory).printAdjacentTerritories();                   
                            continue;                                
                        }

                        //Checks if territory is valid
                        if (territories.containsKey(defendingTerritory)) {
                            //Checks if defending territory is adjacent to attacking territory
                            if (territories.get(defendingTerritory).getOwner().equals(currentPlayer)) {
                                System.out.println("Cannot attack a territory you own!");
                                continue;
                            }

                            for (String adjTerritory : territories.get(attackingTerritory).getAdjacentTerritories()) {
                                if (defendingTerritory.equals(adjTerritory)) {
                                    validTerritoryTo = true;
                                    break;
                                }                                                                                   
                            }
                            if (!validTerritoryTo) {
                                System.out.println("Territory is not adjacent\n" +
                                    "For a list of adjacent territories, type 'list-adjacent'\n");
                            }
                        }                                                             
                        else { //Territory not valid
                            System.out.println("Please choose a valid territory\n" +
                                    "For a list of adjacent territories, type 'list-adjacent'\n");
                        }                                   
                    } while(!validTerritoryTo);                
                    System.out.println();

                    replay.update(attackingTerritory + "attacks" + defendingTerritory);
                    //Attacking command
                    commandManager.executeCommand(new AttackCommand(territories.get(attackingTerritory), territories.get(defendingTerritory)));
                    
                    System.out.println("Continue Attacking? (y/n)");
                }// End if for attacking
                replay.upload();
            } while(userInput.equalsIgnoreCase("y"));
                       
            currentPlayer.drawCards();  //draws card if player captures territory
                        
            //---------------------------------------------------------Fortifying-------------------------------------------------------//
            //TODO: execute when currentPlayer has more than 1 territory
            formattedMessage("Fortifying Phase");
            System.out.println("\n- Choose a territory you would like to move troops to: "); 
            
            do {                      
                System.out.println("\nChoose:\n\t1) A territory to move troops from\n\t2) An adjacent territory to move troops into\n");
                System.out.print("- Type in name of territory to display adjacent territories.\n- Press \"Enter\" to fortify\n");
                printOwnedTerritory(currentPlayer); 
                System.out.println();
                territoryName = sc.nextLine();                              
                
                if (territories.containsKey(territoryName)) {                        
                    Territory currentTerritory = territories.get(territoryName);
                    System.out.println("\n|| Adjacent Territories ||");
                    currentTerritory.printAdjacentTerritories();                                                                                                           
                } 
                else if (!territories.containsKey(territoryName) && !territoryName.isEmpty()) {
                    System.out.println("Enter in a valid territory: ");
                } 
                else {
                    
                }                   
                
            } while(!territoryName.isEmpty());
                    
            // Fortifying procedure
            do {
                String territoryFrom = "";
                String territoryTo = "";  
                
                //until player enters valid territory fromm
                do {                
                    System.out.print("Territory from: "); //prompts user input                                            
                    String territoryInput = sc.nextLine();
                                                    
                    if(territoryInput.equals("end")) {
                        break;
                    }
                    
                    if (!territories.containsKey(territoryInput)) {                                     //check if territory exists
                        System.out.println("Enter in a valid territory");
                    } else if (!(territories.get(territoryInput).getOwner().equals(currentPlayer))) {   //check if player owns territory
                        System.out.println("Cannot select enemy territory");        
                    } else {
                        if (territories.containsKey(territoryInput)) {                                  //check if player has adjacent territories to fortify  
                            int i = 0;  
                            boolean hasAdjacent = false;                                            
                            String adjacentTerritories[] = territories.get(territoryInput).getAdjacentTerritories();           

                            while (i < adjacentTerritories.length && !hasAdjacent) {                   
                                if (territories.get(adjacentTerritories[i]).getOwner().equals(currentPlayer)) { //checks if currentPlayer controls any adjacent territory
                                    hasAdjacent = true;                            
                                }
                                i++;
                            }

                            //TODO: Check if territoryFrom only has 1 troop on it
                            if (hasAdjacent) {
                                territoryFrom = territoryInput;
                            } else {
                                System.out.println("- You do not control any adjacent territory for this territory");
                                System.out.println("- Choose another territory or enter 'end' to end your turn");
                            }    
                        }
                    }                                                                           
                    
                } while (territoryFrom.equals(""));
                   
                if (!territoryFrom.equals("")) {    //If territoryFrom is not empty, user entered a valid territory to fortify from, else end turn
                    //loops until player enters valid territory to
                    do {                           
                        System.out.print("Territory To: ");   //prompts user input
                        String territoryInput = sc.nextLine();          

                        if(territoryInput.equals("list-adjacent")) {
                            System.out.println("\n|| Adjacent territories to fortify ||");                            
                            territories.get(territoryFrom).printAdjacentTerritories();
                            System.out.println();
                            continue;                                
                        }

                        if (!territories.containsKey(territoryInput)) {                                     //check if territory exists
                            System.out.println("Enter in a valid territory."
                                    + " For a list of adjacent territories, type 'list-adjacent'");
                        } else if(!(territories.get(territoryInput).getOwner().equals(currentPlayer))) {    //check if player owns territory
                            System.out.println("Cannot select enemy territory."
                                    + " For a list of adjacent territories, type 'list-adjacent'");
                        } else {                                                                            //check if territory is adjacent
                            for (String adjTerritory : territories.get(territoryFrom).getAdjacentTerritories()) { 
                                if (territoryInput.equals(adjTerritory)) {
                                    territoryTo = territoryInput;
                                }                                                                                   
                            }
                        }

                    } while (territoryTo.equals(""));
                
                    //fortifying the territory
                    commandManager.executeCommand(new MoveInArmiesCommand(territories.get(territoryFrom), territories.get(territoryTo)));  
                }

                System.out.println("Press any key to end your turn (type \"undo\" to  undo action)");
                userInput = sc.nextLine();
                                
                if (userInput.equals("undo")){
                    commandManager.undo();  
                }
                                              
            } while(userInput.equals("undo"));  
      
            try {
                Tweeter.TweetTerritoriesConquered(currentPlayer);
            } catch (Exception e){
                System.out.println("ERROR: Tweet didn't work");
            } 
                   
            playerIndex--;
            if (playerIndex == -1) {
                playerIndex = playerList.size() - 1;                
            }
            
            if (playerMap.get(playerList.get(playerIndex)).getPlayerName().equals("Neutral")) {
                playerIndex--;
            } 
            
            currentPlayer = playerMap.get(playerList.get(playerIndex));    
            
            if (playerList.size() == 2 && playerList.contains("Neutral") || playerList.size() == 1) {
                isPlaying = false;
            }
            
            currentPlayer = playerMap.get(playerList.get(playerIndex));
            replay.upload();
            //isPlaying will be false when playerList == 1   
        } //End while

        // Tweet number of territories conquered at the end of the game.
        try {
            Tweeter.TweetEndOfGame(playerMap);
        } catch (Exception e){
            System.out.println("ERROR: Tweet didn't work");
        }
    } //End main
    
    private static void printOwnedTerritory(Player currentPlayer) {        
        System.out.println("\n|| Your Territories ||");        
        for(Map.Entry<String, Territory> x: territories.entrySet()) {  //Prints out Player's territory and the num of troops on them           
            if (x.getValue().getOwner() == currentPlayer) {    
                System.out.print(padRight(x.getValue().getTerritoryName().toString(), 25) + " || ");
                System.out.println(x.getValue().getNumArmies());
            }            
        }
    }
    
    public static void formattedMessage(String gameMessage) {
        System.out.println("\n-----------------------------------------------");
        System.out.printf("\t\t%s\n", gameMessage);
        System.out.println("-----------------------------------------------\n");
    }
    
    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s); 
    }
}