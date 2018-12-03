import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.concurrent.*;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Main {
    
    //........Data Structures........//
    static Map<String,Territory> territories = new HashMap<>();
    static LinkedHashMap<String, Player> playerMapTest = new LinkedHashMap<>();
    static Iterator<Map.Entry<String, Player>> playerMapIterator = playerMapTest.entrySet().iterator();
    
    //........Objects........//
    static Player currentPlayer;    
    static Deck deck = new Deck();
    
    //........Timeout/Undo/Replay objects........//
    static CommandManager commandManager = new CommandManager();    
    static Replay replay = new Replay();
    static ExecutorService executor = null;
    static Future<String> future = null;
    
    //........Variables........//
    static int timeOut = 30;   // max wait time before game times out    
    static boolean timedOut = false;
    static boolean isPlaying = true;
    static boolean troopsMoved = false;
    static boolean isAttacking = false;
    static boolean fortifying = false;
    static String input;
    
    // Main 
    public static void main(String[] args) throws IOException, InterruptedException, Exception {       
        startGame();                
        formattedMessage("GAME OVER!");
    }         
    
    // Start game function
    protected static void startGame() throws IOException, InterruptedException, Exception {               
        
        // 3 Phases:
        // 1) Calculate Armies & place infantry
        // 2) Attack
        // 3) Fortify 
    
        ApiContextInitializer.init();
                
        TelegramBotsApi riskBot = new TelegramBotsApi();
        try {
            riskBot.registerBot(TelegramJoinBot.getInstance());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        
        }  
        
        Scanner sc = new Scanner(System.in);
        int playerIndex = Setup.getInstance().getStartingPlayerIndex();                             
        executor = Executors.newSingleThreadExecutor();        
        currentPlayer = getNextPlayer();                
        
        while(isPlaying) { //until only 1 player occupies territories(except for neutral in 2 player games)            
            
            // Skips Neutral's turn
            if(currentPlayer.getPlayerName().equals("Neutral"))
                currentPlayer = getNextPlayer();
            
            // if timed out, reset timedOut
            if (timedOut) {                               
                System.out.println("\n-------- Player timed out, Moving on to next player --------"); 
                System.out.println("\n------------------ press \"Enter\" twice -------------------");                
                sc.nextLine();
                timedOut = false;                
            }
            
            //--------------------------------------------------------ANNOUNCING ROUND-------------------------------------------------------------------//
            formattedMessage(currentPlayer.getPlayerName() + "'s turn");
            replay.update("Player " + playerIndex + "'s turn");
            
            //------------------------------------------------CALCULATE ARMIES & PLACING INFANTRY--------------------------------------------------------//                                              
            formattedMessage("PLACING INFANTRY PHASE");            
            currentPlayer.updatePlaceableInfantry(currentPlayer.calculateInfantry());                                                                                  
            calculateAndPlaceInfantry();
            replay.upload();           
                        
            // if timed out, move on to next player
            if (timedOut) {     
                nextRound();         
                continue;
            }
                                                     
            //-------------------------------------------------------------ATTACK------------------------------------------------------------------------//
            formattedMessage("ATTACKING PHASE");             
            attackPhase();            
            currentPlayer.drawCards();  //draws card if player captures territory
            replay.upload();
                        
            // if timed out, move on to next player
            if (timedOut) {     
                nextRound();
                continue;
            }
                        
            //------------------------------------------------------------Fortifying---------------------------------------------------------------------//            
            formattedMessage("FORTIFYING PHASE");            
            fortifyPhase();                        
            
            try {
                Tweeter.TweetTerritoriesConquered(currentPlayer);
            } catch (Exception e){
                System.out.println("ERROR: Tweet didn't work");
            } 
                               
            nextRound();
            replay.upload();           
        } //End while

        // Tweet number of territories conquered at the end of the game.
        try {
            Tweeter.TweetEndOfGame(playerMapTest);
        } catch (Exception e){
            System.out.println("ERROR: Tweet didn't work");
        }

        executor.shutdownNow();
    }
                      
    // Calculates placeable infantry and asks player to place troops
    // Continues until player places all troops or is timed out    
    protected static void calculateAndPlaceInfantry() {        
        if (currentPlayer.getPlaceableInfantry() > 0) {                                 // determine if player has more than 0 troops       
            System.out.printf("- Place your remaining armies on your territories\n", currentPlayer.getPlaceableInfantry());           
            while (currentPlayer.getPlaceableInfantry() > 0 && !timedOut) {             // loops while player has more than 0 troops & is not timed out                                            
                troopsMoved = false;                
                do {                                                                    // loops while Player still has armies to place 
                    System.out.println("- Remaining armies: " + currentPlayer.getPlaceableInfantry());
                    System.out.println("- Choose which territory you would like to fortify: ");                    
                    printOwnedTerritory(currentPlayer);
                    System.out.println();

                    replay.update("- Remaining armies: " + currentPlayer.getPlaceableInfantry());

                    String territoryName = timeoutPrompt(executor, future, timeOut);    // Timeout waits for user response 

                    if (isTimedOut(territoryName)) {
                        continue;
                    }
                    
                    if (territoryName.equals("undo")) {                                 // Check if user types "undo"
                        commandManager.undo();                            
                        continue;
                    } 
                                                           
                    boolean isValidTerritory = territories.containsKey(territoryName);  // Checks if territory is valid                                                                
                    
                    if (!isValidTerritory) {                                            // Invalid territory
                        System.out.println("Enter in a valid territory");
                    } else {                                                            // Valid territory 
                        Territory currentTerritory = territories.get(territoryName);
                                                
                        if (currentTerritory.getOwner() != currentPlayer) {             // Player does not own territory
                            System.out.println("You cannot place troops on an enemy territory!");
                        } else {                                                        // Player owns territory
                            placeInfantry(territoryName);                          
                        }                                                                      
                    }  
                    
                } while (!troopsMoved && !timedOut); // end do-while
                
                System.out.println();                                
            } //end while            
        } //end if
    }
    
    // Function to place infantries
    // If timed out, function returns nothing
    protected static void placeInfantry(String territoryName) {
        System.out.printf("How many troops would you like to move to %s? ", territoryName);                           
        input = timeoutPrompt(executor, future, timeOut);    

        if (isTimedOut(input)) {
            return;
        }

        // New variables: troopsToMove & valid number of troops
        int troopsToMove = Integer.parseInt(input);
        boolean validNumTroops = troopsToMove <= currentPlayer.getPlaceableInfantry() && troopsToMove > 0;

        // Loops while invalid number of troops
        while (!validNumTroops) {
            System.out.println("Invalid number of troops to move.");
            System.out.printf("How many troops would you like to move to %s? ", territoryName);
            input = timeoutPrompt(executor, future, timeOut);   

            if (isTimedOut(input)) {
                return;
            }

            troopsToMove = Integer.parseInt(input);
            validNumTroops = troopsToMove <= currentPlayer.getPlaceableInfantry() && troopsToMove > 0;
        }

        //Executes command & fortifies the territory                            
        if (validNumTroops) {
            troopsMoved = true;
            commandManager.executeCommand(new FortifyingTerritoriesCommand(currentPlayer, territoryName, troopsToMove)); 
            System.out.printf("\n- Added %d armies to %s", troopsToMove, territoryName);
            replay.update("n- Added " + troopsToMove + " armies to " + territoryName);
        } 
    }     
    
    // Displays attack options
    protected static void displayAttackOptions() {
        System.out.println("\nChoose:\n\t1) A territory to attack from\n\t2) An adjacent territory to attack\n"); 
        System.out.print("- Type in name of territory to display adjacent territories.\n- Press \"Enter\" to continue with attack\n");            
        printOwnedTerritory(currentPlayer);
    }
    
    // Check if territory exists and is owned by current player
    protected static boolean isFriendlyTerritory(String territory) {
                
        if (territories.containsKey(territory)) {
            Territory currentTerritory = territories.get(territory);   
            if (currentTerritory.getOwner() == currentPlayer) {
                return true;
            }
        }       
        
        return false;
    }
    
    // Check if territory exists and is owned by another player
    protected static boolean isEnemyTerritory(String territory) {
                
        if (territories.containsKey(territory)) {
            Territory currentTerritory = territories.get(territory);   
            if (currentTerritory.getOwner() != currentPlayer) {
                return true;
            }
        }       
        
        return false;
    }
    
    // Check if attacking territory is valid, returns territory name
    protected static String getAttackingTerritory() {
        boolean validAttackingTerritory = false; 
        String attackingTerritory = null;
        while (!validAttackingTerritory) {
            System.out.print("- Choose territory to attack from: ");          // Determine if attacking territory is valid
            input = timeoutPrompt(executor, future, timeOut);       

            if (isTimedOut(input)) {
                isAttacking = false;
                return null;
            }                    
            else if (input.equals("list-owned")) {                            // check if player types 'list-owned'
                //playerMapTest.get(currentPlayer.getPlayerName()).printOwnedTerritories();                       
                printOwnedTerritory(currentPlayer);
                continue;                                
            }                                                                                                    
            else if (isFriendlyTerritory(input)) {                            // valid and is owned by current player
                validAttackingTerritory = true;
                attackingTerritory = input;                
            } 
            else {
                System.out.println("invalid territory\n" +
                        "For a list of owned territories, type 'list-owned'\n");
            }                        
        }
        
        return attackingTerritory;
    }
    
    // Check if defending territory is valid, returns territory name
    protected static String getDefendingTerritory(String attackingTerritory) {
        boolean validDefendingTerritory = false; 
        String defendingTerritory = null;
        while (!validDefendingTerritory) {
            System.out.print("- Choose territory to attack: ");          // Determine if attacking territory is valid
            input = timeoutPrompt(executor, future, timeOut);       

            if (isTimedOut(input)) {
                isAttacking = false;
                return null;
            }                    
            else if(input.equals("list-adjacent")) {                          // Checks if player types "adjacent"
                territories.get(attackingTerritory).printAdjacentTerritories();                   
                continue;                                
            }                                                                                                       
            else if (isEnemyTerritory(input)) {                               // valid and is owned by current player
                validDefendingTerritory = true;
                defendingTerritory = input;                
            } 
            else {
                System.out.println("invalid territory\n" +
                        "For a list of adjacent territories, type 'list-adjacent'\n");
            }                        
        }
        
        return defendingTerritory;
    }
    
    // Attack prompt.
    // If player wants to attack, it calls attack() function
    protected static void attackPhase() {    
        System.out.print("\nWould you like to attack this turn?: (y/n) ");
        isAttacking = false;
                
        input = timeoutPrompt(executor,future, timeOut);
        
        if (isTimedOut(input)) {
            return;
        }
        
        // CASES:
        // 1) user enters "y"
        if (input.equalsIgnoreCase("y")) {
            // Loops while player not timed out
            while (!isAttacking && !timedOut) {
                displayAttackOptions();

                input = timeoutPrompt(executor, future, timeOut);   

                if (isTimedOut(input)) {
                    return;
                }

                boolean validTerritory = territories.containsKey(input);               

                // CASES
                // 1) presses enter (move to attackPhase())            
                if (input.isEmpty()) {
                    isAttacking = true;
                    attack();      
                    break;
                }            
                // 2) chooses valid territory
                else if (validTerritory) {
                    Territory currentTerritory = territories.get(input);
                    currentTerritory.printAdjacentTerritories();                                                           
                }             
                // 3) chooses invalid territory
                else {
                    System.out.println("Invalid territory/option");                
                }
            }            
        }      
        
        // 2) user does not enter "y", do nothing
    }   
    
    // Attacking function. Loops while isAttacking
    protected static void attack() {

        while (isAttacking) {
            // Checking if territory attacking from is valid                             
            String attackingTerritory;
            String defendingTerritory;
            
            //------Checks if attacking territory is valid------//
            attackingTerritory = getAttackingTerritory();
            
            //------Checks if defending territory to is valid------//
            defendingTerritory = getDefendingTerritory(attackingTerritory);
                
            // timed out if null
            if (attackingTerritory == null || defendingTerritory == null) {
                return;
            }
            
            // Assigning attacking & defending territories
            Territory attacker = territories.get(attackingTerritory);                   
            Territory defender = territories.get(defendingTerritory);
            
            replay.update(attackingTerritory + " attacks " + defendingTerritory);            
            commandManager.executeCommand(new AttackCommand(attacker, defender)); //Attacking command
            System.out.println("Continue Attacking? (y/n)");
            
            input = timeoutPrompt(executor,future, timeOut);
        
            if (isTimedOut(input)) {                                // timed out check
                return;
            }

            while (input.equals("undo")) {                          // "undo" check
                commandManager.undo();
                System.out.println("Continue Attacking? (y/n)");                  
                input = timeoutPrompt(executor, future, timeOut);   

                if (isTimedOut(input)) {
                    return;
                }  
            }
            
            if (input.equalsIgnoreCase("y")) {
                displayAttackOptions();
                isAttacking = true;                
            } else {
                isAttacking = false;
            }
            
        }// End while for attacking
        replay.upload();
    } 
                
    // Fortifying prompt
    // If player wants to fortify, it calls fortify() function
    protected static void fortifyPhase() {        
        System.out.println("\n- Choose a territory you would like to move troops to: ");        
        boolean validTerritory = false;
        fortifying = false;
                        
        while (!validTerritory && !timedOut) {                                               
            System.out.println("\nChoose:\n\t1) A territory to move troops from\n\t2) An adjacent territory to move troops into\n");
            System.out.print("- Type in name of territory to display adjacent territories.\n- Press \"Enter\" to fortify\n");
            printOwnedTerritory(currentPlayer); 
            System.out.println();
            input = timeoutPrompt(executor, future, timeOut);   

            if (isTimedOut(input)) {
                return;
            }                          

            // CASES:
            // 1) empty (fortify())
            if (input.isEmpty()) {
                validTerritory = true;
                fortify();
                break;
            }
            // 2) friendly territory 
            else if (isFriendlyTerritory(input)) {
                Territory currentTerritory = territories.get(input);
                currentTerritory.printAdjacentTerritories(); 
            } 
            // 3) invalid territory
            else {
                System.out.println("Enter in a valid territory: ");
            }
        }                       
    }
    
    // Fortify loop
    protected static void fortify() {
        fortifying = true;
        while (fortifying) {
            String territoryFrom = "";
            String territoryTo = ""; 
                           
            //------Checks if fortifying territory is valid------//
            territoryFrom = getFortifyingTerritory();                  
            
            //------Checks if fortified territory is valid------//
            territoryTo = getFortifiedTerritory(territoryFrom);
            
            // timed out if null
            if (territoryFrom == null || territoryTo == null) {
                return;
            }
                        
            // Territory from & to
            Territory tFrom = territories.get(territoryFrom);
            Territory tTo = territories.get(territoryTo);
            
            //fortifying the territory
            commandManager.executeCommand(new MoveInArmiesCommand(tFrom,tTo));  
            
            System.out.println("Press any key to end your turn (type \"undo\" to  undo action)");
            input = timeoutPrompt(executor, future, timeOut);   
                     
            if (isTimedOut(input)) {                
                return;
            }

            if (input.equals("undo")){
                commandManager.undo();
                fortifyPhase();
            }
            else {
                fortifying = false;
            }
        }    
    } 
    
    // Checks if the adjacent territory to fortify is valid
    protected static String getAdjacentTerritory() {        
        String territoryFrom = null;
        boolean hasAdjacent = false;                                            
        String adjacentTerritories[] = territories.get(input).getAdjacentTerritories();     // Array of adjacent territories
        Territory adjacentTerritory = null;

        for (int i = 0; i < adjacentTerritories.length && !hasAdjacent; i++) {
            if (territories.get(adjacentTerritories[i]).getOwner().equals(currentPlayer)) { //checks if currentPlayer controls any adjacent territory
                hasAdjacent = true;   
                adjacentTerritory = territories.get(input);     
            }
        }                                        
        
        if (hasAdjacent && adjacentTerritory.getNumArmies() > 1) {            
            territoryFrom = input;
        } else {            
            System.out.println("- Either you don't control any adjacent territories or you are attempting to choose a territory with 1 troop on it");
            System.out.println("- Choose another territory or enter 'end' to end your turn");
        }    
        
        return territoryFrom;
    }
    
    // Checks if territory fortifying from is valid
    protected static String getFortifyingTerritory() {
        boolean validTerritoryFrom = false;
        String territoryFrom = null;
        
        // Determine if territory from is valid and is owned by cur player
        while (!validTerritoryFrom) {        
            System.out.print("Territory from: "); //prompts user input                                            
            input = timeoutPrompt(executor, future, timeOut);   
            
            if (isTimedOut(input) || input.equals("end")) {     // timed out or user enters "end"
                fortifying = false;
                return null;
            }
            else if (input.equals("list-owned")) {              // check if player types 'list-owned'                                                     
                printOwnedTerritory(currentPlayer);
                continue;                                
            }                           
            else if (isFriendlyTerritory(input)) {              // territory is valid
                territoryFrom = getAdjacentTerritory();
                if (territoryFrom != null) {
                    validTerritoryFrom = true;
                }
            }                
            else {                                              // Invalid territory
                System.out.println("invalid territory\n" +
                       "For a list of owned territories, type 'list-owned'\n");
            }                                                       
        }
        
        return territoryFrom;
    }
            
    // Checks if territory being fortified is valid
    protected static String getFortifiedTerritory(String fortifyingTerritory) {
                        
        boolean validTerritoryTo = false;
        String territoryTo = null;
        
        if (fortifyingTerritory == null) {
            return null;
        }
        
        while (!validTerritoryTo) {                                     // Determine if territory to is valid and is owned by cur player
            System.out.print("Territory to: "); //prompts user input                                            
            input = timeoutPrompt(executor, future, timeOut);   

            if (isTimedOut(input) || input.equals("end")) {             // timed out or "end" entered
                fortifying = false;
                return null;
            }
            else if (input.equals("list-adjacent")) {                   // list-adjacent                              
                territories.get(fortifyingTerritory).printAdjacentTerritories();                      
                continue;                                
            }
            else if (isFriendlyTerritory(input)) {                      // is friendly territory
                for (String adjTerritory : territories.get(fortifyingTerritory).getAdjacentTerritories()) { 
                    if (input.equals(adjTerritory)) {                            
                        validTerritoryTo = true;
                        territoryTo = input;
                    }                                                                                   
                }                             
            }                
            else {                                                      // Invalid territory
                System.out.println("Enter in a valid territory."
                            + " For a list of adjacent territories, type 'list-adjacent'");
            }                                                       
        }
        
        return territoryTo;   
    }
                                        
    // Function to print owned territories
    protected static void printOwnedTerritory(Player currentPlayer) {        
        System.out.println("\n|| Your Territories ||");        
        for(Map.Entry<String, Territory> x: territories.entrySet()) {  //Prints out Player's territory and the num of troops on them           
            if (x.getValue().getOwner() == currentPlayer) {    
                System.out.print(padRight(x.getValue().getTerritoryName().toString(), 25) + " || ");
                System.out.println(x.getValue().getNumArmies());
            }            
        }
    }
    
    // Function to format messages
    public static void formattedMessage(String gameMessage) {
        System.out.println("\n-----------------------------------------------");
        System.out.printf("\t\t%s\n", gameMessage);
        System.out.println("-----------------------------------------------\n");
    }
       
    // Function to help format message
    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s); 
    }

    // Function to check if player is still playing
    protected static boolean checkIfStillPlaying(boolean isPlaying) {
        if (playerMapTest.size() == 2 && playerMapTest.keySet().contains("Neutral") || playerMapTest.size() == 1) {
                isPlaying = false;
        }      
        
        return isPlaying;
    }

    // Prompt waits 30 secs before timing out. Returns user input    
    protected static String timeoutPrompt(ExecutorService executor, Future<String> future, int timeOut) {
        Task task = new Task();
        future = executor.submit(task); 
        String userInput = null;
        
        try {                                             
            System.out.println(future.get(timeOut, TimeUnit.SECONDS));             
            userInput = task.response();                      
        } catch (Exception e) {                    
            future.cancel(true);                  
        }
              
        return userInput;
    }

    // Function to determine if player is timed out
    protected static boolean isTimedOut(String input) {
        if (input == null) {
            timedOut = true;            
        }
        
        return timedOut;
    }
    
    // resets variable for next round
    protected static void nextRound() {        
        currentPlayer = getNextPlayer();
        isPlaying = checkIfStillPlaying(isPlaying); 
        
        troopsMoved = false;
        isAttacking = false;
        fortifying = false;
        input = null;
    }
    
    // returns new nextPlayer
    static Player getNextPlayer() {
        // If iterator is on the last player, restart at first player
        if(!playerMapIterator.hasNext())
            playerMapIterator = playerMapTest.entrySet().iterator();
        return playerMapIterator.next().getValue();

    }
}

// Class used for timeout  
// times out if user does not enter anything within timeout time
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

