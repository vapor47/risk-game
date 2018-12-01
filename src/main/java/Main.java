import java.util.*;
import java.util.concurrent.*;

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

    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        ExecutorService executor = null;
        Future<String> future = null;                
        int timeOut = 30;   // max wait time before game times out

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
        boolean timedOut = false;
        String territoryName;
        String userInput;
        
        Player currentPlayer = playerMap.get(playerList.get(playerIndex));
        
        while(isPlaying){ //until only 1 player occupies territories(except for neutral in 2 player games)
            //------------------------------------------------CALCULATE ARMIES & PLACING INFANTRY--------------------------------------------------------//                        
            //System.out.println("PlayeList.size = " + playerList.size());
            executor = Executors.newSingleThreadExecutor(); 

            if (timedOut) {                
                System.out.println("\n- Player timed out, Moving on to next player...");
                System.out.println("--------Press \"enter\" twice to continue--------");       
                String input = sc.nextLine();
                timedOut = false;                
            }

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
                        
                        territoryName = timeoutPrompt(executor, future, timeOut);   
                
                        if (territoryName == null) {
                            timedOut = true;                            
                            break;                            
                        }   
                        
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
                            userInput = timeoutPrompt(executor, future, timeOut);   
                            
                            if (userInput == null) {
                                timedOut = true;                                
                                break;                               
                            }
                            
                            int troopsToMove = Integer.parseInt(userInput);
                            boolean validNumTroops = troopsToMove <= currentPlayer.getPlaceableInfantry() && troopsToMove > 0;
                            while (!validNumTroops) {
                                System.out.println("Invalid number of troops to move.");
                                System.out.printf("How many troops would you like to move to %s? ", territoryName);
                                userInput = timeoutPrompt(executor, future, timeOut);   
                
                                if (userInput == null) {
                                    timedOut = true;                                    
                                    break;                                    
                                }                              
                                troopsToMove = Integer.parseInt(userInput);
                                validNumTroops = troopsToMove <= currentPlayer.getPlaceableInfantry() && troopsToMove > 0;
                            }
                                      
                            //Executes command & fortifies the territory                            
                            if (validNumTroops) {
                                commandManager.executeCommand(new FortifyingTerritoriesCommand(currentPlayer, territoryName, troopsToMove)); 
                                System.out.printf("\n- Added %d armies to %s", troopsToMove, territoryName);
                                replay.update("n- Added " + troopsToMove + " armies to " + territoryName);
                                troopsMoved = true;
                            }   
                            
                        } //Valid, but player does not own the territory      
                        else {
                            System.out.println("Cannot place troops into enemy territory");
                        }                      

                        if (timedOut) {
                            break;
                        }
                          
                    } while (!troopsMoved);
                    System.out.println();

                    if (timedOut) {
                        break;
                    }
                } //end while

                if (timedOut) {
                    playerIndex = getNextPlayer(playerIndex);            
                    currentPlayer = playerMap.get(playerList.get(playerIndex));
                    isPlaying = checkIfStillPlaying(isPlaying);
                    continue;
                }
            } //end if
            
            replay.upload();
            
            //-------------------------------------------------------------ATTACK------------------------------------------------------------------------//
            formattedMessage("Attacking Phase");
            System.out.print("\nWould you like to attack this turn?: (y/n)");

            do {                
                userInput = timeoutPrompt(executor, future, timeOut);        

                if (userInput == null) {
                    timedOut = true;                    
                    break;                    
                }    

                // Checks if user types "undo"
                while (userInput.equals("undo")) {
                        commandManager.undo();
                        System.out.println("Continue Attacking? (y/n)");
                        // userInput = sc.nextLine();
                        userInput = timeoutPrompt(executor, future, timeOut);   

                        if (userInput == null) {
                            timedOut = true;                        
                            break;                      
                         }          
                }

                if (timedOut) {
                    break;
                }

                //Prints out Player's territory and the num of troops on them        
                //Attack Prompt
                if (userInput.equalsIgnoreCase("y")) {                    
                    System.out.println();                                    
                    System.out.println("\nChoose:\n\t1) A territory to attack from\n\t2) An adjacent territory to attack\n"); 
                    System.out.print("- Type in name of territory to display adjacent territories.\n- Press \"Enter\" to continue with attack\n");
                    printOwnedTerritory(currentPlayer);
                    
                    territoryName = timeoutPrompt(executor, future, timeOut);   

                    if (territoryName == null) {
                        timedOut = true;
                        break;                        
                    }         
                    
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

                        territoryName = timeoutPrompt(executor, future, timeOut);   

                        if (territoryName == null) {
                            timedOut = true;
                            break;                        
                        }          

                    } while (!(territoryName.isEmpty()));

                    if (timedOut) {
                        break;
                    }

                    //Checking if territory attacking from is valid
                    String attackingTerritory;
                    boolean validTerritoryFrom = false;                
                    do {                    
                        System.out.print("- Choose territory to attack from: ");
                        attackingTerritory = timeoutPrompt(executor, future, timeOut);   

                        if (attackingTerritory == null) {
                            timedOut = true;
                            break;                        
                        }     

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

                    if (timedOut) {
                        break;
                    }

                    //Checking if territory attacking is valid
                    String defendingTerritory;
                    boolean validTerritoryTo = false;
                    do {
                        System.out.print("- Choose territory to attack: ");
                        defendingTerritory = timeoutPrompt(executor, future, timeOut);   

                        if (defendingTerritory == null) {
                            timedOut = true;
                            break;                        
                        }    

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

                    if (timedOut) {
                        break;
                    }

                    replay.update(attackingTerritory + "attacks" + defendingTerritory);
                    //Attacking command
                    commandManager.executeCommand(new AttackCommand(territories.get(attackingTerritory), territories.get(defendingTerritory)));
                    
                    System.out.println("Continue Attacking? (y/n)");
                }// End if for attacking
                replay.upload();
            } while(userInput.equalsIgnoreCase("y"));
                       
            currentPlayer.drawCards();  //draws card if player captures territory
                        
            if (timedOut) {
                playerIndex = getNextPlayer(playerIndex);            
                currentPlayer = playerMap.get(playerList.get(playerIndex));
                isPlaying = checkIfStillPlaying(isPlaying);
                continue;
            }

            //---------------------------------------------------------Fortifying-------------------------------------------------------//
            //TODO: execute when currentPlayer has more than 1 territory
            formattedMessage("Fortifying Phase");
            System.out.println("\n- Choose a territory you would like to move troops to: "); 
            
            do {                      
                System.out.println("\nChoose:\n\t1) A territory to move troops from\n\t2) An adjacent territory to move troops into\n");
                System.out.print("- Type in name of territory to display adjacent territories.\n- Press \"Enter\" to fortify\n");
                printOwnedTerritory(currentPlayer); 
                System.out.println();
                territoryName = timeoutPrompt(executor, future, timeOut);   

                if (territoryName == null) {
                    timedOut = true;
                    break;                        
                }                               
                
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
                    
            // If timed out, move on to next player
            if (timedOut) {
                playerIndex = getNextPlayer(playerIndex);            
                currentPlayer = playerMap.get(playerList.get(playerIndex));
                isPlaying = checkIfStillPlaying(isPlaying);
                continue;
            }

            // Fortifying procedure
            do {
                String territoryFrom = "";
                String territoryTo = "";  
                
                //until player enters valid territory fromm
                do {                
                    System.out.print("Territory from: "); //prompts user input                                            
                    String territoryInput = timeoutPrompt(executor, future, timeOut);   
                                     
                    if (territoryInput == null) {
                        timedOut = true;
                        break;                        
                    }

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
                   
                if (timedOut) {
                    break;
                }

                if (!territoryFrom.equals("")) {    //If territoryFrom is not empty, user entered a valid territory to fortify from, else end turn
                    //loops until player enters valid territory to
                    do {                           
                        System.out.print("Territory To: ");   //prompts user input
                        String territoryInput = timeoutPrompt(executor, future, timeOut);   

                        if (territoryInput == null) {
                            timedOut = true;
                            break;                        
                        }            

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
                
                    if (timedOut) {
                        break;
                    }

                    //fortifying the territory
                    commandManager.executeCommand(new MoveInArmiesCommand(territories.get(territoryFrom), territories.get(territoryTo)));  
                }

                System.out.println("Press any key to end your turn (type \"undo\" to  undo action)");
                userInput = timeoutPrompt(executor, future, timeOut);   

                if (userInput == null) {
                    timedOut = true;
                    break;                        
                }    
                                
                if (userInput.equals("undo")){
                    commandManager.undo();  
                }
                                              
            } while(userInput.equals("undo"));  
      
            try {
                Tweeter.TweetTerritoriesConquered(currentPlayer);
            } catch (Exception e){
                System.out.println("ERROR: Tweet didn't work");
            } 
                   
            // Moves on to next player weather timed out or not
            playerIndex = getNextPlayer(playerIndex);            
            currentPlayer = playerMap.get(playerList.get(playerIndex));
            isPlaying = checkIfStillPlaying(isPlaying); 

            replay.upload();
            //isPlaying will be false when playerList == 1   
        } //End while

        // Tweet number of territories conquered at the end of the game.
        try {
            Tweeter.TweetEndOfGame(playerMap);
        } catch (Exception e){
            System.out.println("ERROR: Tweet didn't work");
        }

        executor.shutdownNow();
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

    // int nextPlayerIndex = getNextPlayer(playerIndex)
    // currentPlayer = playerMap.get(playerList.get(nextPlayerIndex))
    // checkIfStillPlaying(isPlaying);
    private static int getNextPlayer(int curPlayerIndex) { // return new player index
        int playerIndex = --curPlayerIndex;
        
        if (playerIndex == -1) {
            playerIndex = playerList.size() - 1;                
        }

        if (playerMap.get(playerList.get(playerIndex)).getPlayerName().equals("Neutral")) {
            playerIndex--;
        }    

        return playerIndex;
    }
    
    private static boolean checkIfStillPlaying(boolean isPlaying) {
        if (playerList.size() == 2 && playerList.contains("Neutral") || playerList.size() == 1) {
                isPlaying = false;
        }      
        
        return isPlaying;
    }

    // Prompt waits 30 secs before timing out
    // returns userInput
    private static String timeoutPrompt(ExecutorService executor, Future<String> future, int timeOut) {
        Task task = new Task();
        future = executor.submit(task); // future is set to new task called Attack Task
        String userInput = null;
        
        try {                                 
            //System.out.printf("\n(Game will move on to next player within %d seconds)\n", timeOut);
            System.out.println(future.get(timeOut, TimeUnit.SECONDS));             
            userInput = task.response();                      
        } catch (Exception e) {                    
            future.cancel(true);                  
        }
              
        return userInput;
    }            
}

// Class used for timeout  
// If user does not enter something within the timeout time, 
// game will continue to the next player
class Task implements Callable<String> {        
    Scanner sc = new Scanner(System.in);          
    String userInput = "";

    @Override
    public String call() throws Exception {            
        //perform task                           
        if (!Thread.interrupted()){                   
            try {                                        
                userInput = sc.nextLine();                                                   
            } catch (Exception e) {                    
                userInput = "";
            }                
        }                                                                        

        return "";
    }

    public String response() {
        return userInput;
    }
}

