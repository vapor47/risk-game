import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Setup {
    private int numPlayers;
    private int startingPlayer;

    Setup(){
        Scanner input = new Scanner(System.in);


        System.out.print("Welcome to Risk!\nHow many people are playing(2-6): ");
        numPlayers = input.nextInt();
        while(numPlayers < 2 || numPlayers > 6) {
            System.out.print("Invalid option! Please enter a number from 2-6: ");
            numPlayers = input.nextInt();
        }       
        startingPlayer = chooseRandomPlayer(numPlayers);
        createTerritories();
        createCards();
        createPlayers(numPlayers);
        giveStartingInfantry(numPlayers);

        if(numPlayers == 2){
            Player neutral = new Player("Neutral");
            twoPlayerStart(neutral);
        }
        else{
            normalStart(startingPlayer);
        }

        // test
        for(Map.Entry<String, Territory> x: Main.territories.entrySet()){
            System.out.print(x.getValue().getTerritoryName() + " || ");
            System.out.println(x.getValue().getOwner().getPlayerName());
        }

    }

    public int getStartingPlayerIndex() {
        return startingPlayer;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
    /* Two Player Set-up
            give both players 14 random territories and place 1 infantry on them
                same for neutral
            then:
                go back and forth between both players
                    1. place 2 infantry on any 1 or 2 territories
                        either 1 & 1, or 2 infantry on 1 territory
                    2. place 1 infantry on any neutral territory
    */
    private void twoPlayerStart(Player neutral){
<<<<<<< HEAD
        for(Map.Entry<String, Territory> x: Main.territories.entrySet()){
            x.getValue().incrementArmies(1);
            int randVal = (int)(Math.random() * 2);
            if(randVal == 2)
                x.getValue().setOwner(neutral);
//            else
//                x.getValue().setOwner(playerList[randVal]);
=======
        /*
            Get array of territories(Map) keys
            Get a random key and assign it to a player and move to next player - repeat 28 times(14 territories for each player)
            Other territories are neutral by default
         */
        ArrayList<String> keys = new ArrayList<String>(territories.keySet());
        int currPlayerIndex = 0;
        for(int i = 42; i > 14; i--){ // runs 28 times
            int index = (int)(Math.random()*i);
            String key = keys.get(index);
            territories.get(key).incrementArmies(1);
            territories.get(key).setOwner(Main.playerMap.get(Main.playerList.get(currPlayerIndex)));
            Main.playerMap.get(Main.playerList.get(currPlayerIndex)).updatePlaceableInfantry(-1);

            currPlayerIndex = (currPlayerIndex + 1) % 2; // goes from 0-1
            keys.remove(index);
        }
        // set remaining Neutral territories armies to 1
        for(String territoryName : keys){
            territories.get(territoryName).incrementArmies(1);
>>>>>>> master
        }
    }

    private void normalStart(int startingPlayerIndex){
        int currPlayerIndex = setPrevPlayer(startingPlayerIndex);
        String currPlayerName = Main.playerList.get(currPlayerIndex);
        Scanner input = new Scanner(System.in);
        String chosenTerritory;

        // Each player goes around placing 1 army onto an unclaimed territory (42 total)
        for(int i = 0; i < 42; i++){
            currPlayerName = Main.playerList.get(currPlayerIndex);
            do {
                System.out.print("\n" + currPlayerName + ", claim a territory: ");
                chosenTerritory = input.nextLine();
                if(!Main.territories.containsKey(chosenTerritory) || !Main.territories.get(chosenTerritory).getOwner().getPlayerName().equals("Neutral")) {
                    if(chosenTerritory.equals("list-unclaimed"))
                        listUnclaimedTerritories();
                    else {
                        System.out.println("That is an invalid option.\n" +
                                "For a list of unclaimed territories, type 'list-unclaimed'");
                    }
                } else { // claim territory for current player
                    Main.playerMap.get(currPlayerName).claimTerritory(Main.territories.get(chosenTerritory));
                    Main.territories.get(chosenTerritory).incrementArmies(1);
                    Main.playerMap.get(currPlayerName).updatePlaceableInfantry(-1);
                }
            } while(!Main.territories.containsKey(chosenTerritory) || !Main.territories.get(chosenTerritory).getOwner().getPlayerName().equals("Neutral"));
            // move to next player
            currPlayerIndex = setPrevPlayer(currPlayerIndex);
        }

        // Now each player places 1 additional army onto any territory they occupy until everyone runs out
        int armiesLeft = Main.playerMap.get(currPlayerName).getPlaceableInfantry();
        for(int i = 0; i < armiesLeft; i++){
            do {
                System.out.print("\n" + currPlayerName + ", choose a territory: ");
                chosenTerritory = input.nextLine();

                // checks to see if player is owner of the territory
                if(Main.playerMap.get(currPlayerName) != Main.territories.get(chosenTerritory).getOwner()) {
                    if(chosenTerritory.equals("list-owned"))
                        Main.playerMap.get(currPlayerName).printOwnedTerritories();
                    else {
                        System.out.println("That is an invalid option.\n" +
                                "For a list of owned territories, type 'list-owned'");
                    }
                } else { // claim territory for current player
                    Main.territories.get(chosenTerritory).incrementArmies(1);
                }
            } while(!Main.territories.containsKey(chosenTerritory));
            currPlayerIndex = setPrevPlayer(currPlayerIndex);
        }
    }

    private int setNextPlayer(int currPlayerIndexNum){
        if(currPlayerIndexNum == (numPlayers - 1))
            return 0;
        else
            return currPlayerIndexNum + 1;
    }

    // Territory claiming portion of set-up goes to the left of the starting player, likely for game balance
    private int setPrevPlayer(int currPlayerIndexNum){
        if(currPlayerIndexNum == 0)
            return numPlayers - 1;
        else
            return currPlayerIndexNum - 1;
    }

    private void listUnclaimedTerritories(){
        for(Map.Entry<String, Territory> x : Main.territories.entrySet()){
            if(x.getValue().getNumArmies() == 0)
                System.out.println(x.getKey());
        }
    }

    // returns number from 0 to (numPlayers - 1)
    private int chooseRandomPlayer(int numPlayers){
        return (int)(Math.random()*numPlayers);
    }

    private void createPlayers(int numPlayers){
        for(int i = 0; i < numPlayers; i++){
            String name = "Player " + (i+1);
            Main.playerList.add(name);
            Main.playerMap.put(name, new Player(name));
        }
    }


    private void giveStartingInfantry(int numPlayers){
        int numInfantry = 0;
        switch (numPlayers){
            case 2:
                numInfantry = 40;
                break;
            case 3:
                numInfantry = 35;
                break;
            case 4:
                numInfantry = 30;
                break;
            case 5:
                numInfantry = 25;
                break;
            case 6:
                numInfantry = 20;
                break;
            default:
                break;
        }
        for(Map.Entry<String, Player> x: Main.playerMap.entrySet())
            x.getValue().updatePlaceableInfantry(numInfantry);
    }
    private void createTerritories(){
        // North America
        Main.territories.put("Alaska", new Territory("Alaska", "North America",
                new String[]{"Alberta","Kamchatka","North West Territory"}));
        Main.territories.put("Alberta", new Territory("Alberta", "North America",
                new String[]{"Alaska","North West Territory","Ontario","Western United States"}));
        Main.territories.put("Central America", new Territory("Central America", "North America",
                new String[]{"Eastern United States","Venezuela","Western United States"}));
        Main.territories.put("Eastern United States", new Territory("Eastern United States", "North America",
                new String[]{"Central America","Ontario","Quebec","Western United States"}));
        Main.territories.put("Greenland", new Territory("Greenland", "North America",
                new String[]{"Iceland","North West Territory","Ontario","Quebec"}));
        Main.territories.put("North West Territory", new Territory("North West Territory", "North America",
                new String[]{"Alaska","Alberta","Greenland","Ontario"}));
        Main.territories.put("Ontario", new Territory("Ontario", "North America",
                new String[]{"Alberta","Eastern United States","Greenland","North West Territory","Quebec","Western United States"}));
        Main.territories.put("Quebec", new Territory("Quebec", "North America",
                new String[]{"Eastern United States","Greenland","Ontario"}));
        Main.territories.put("Western United States", new Territory("Western United States", "North America",
                new String[]{"Alberta","Central America","Eastern United States","Ontario"}));

        // South America
        Main.territories.put("Argentina", new Territory("Argentina", "South America",
                new String[]{"Brazil","Peru"}));
        Main.territories.put("Brazil", new Territory("Brazil", "South America",
                new String[]{"Argentina","North Africa","Peru","Venezuela"}));
        Main.territories.put("Peru", new Territory("Peru", "South America",
                new String[]{"Argentina","Brazil","Venezuela"}));
        Main.territories.put("Venezuela", new Territory("Venezuela", "South America",
                new String[]{"Brazil","Central America","Peru"}));

        // Europe
        Main.territories.put("Great Britain", new Territory("Great Britain", "Europe",
                new String[]{"Iceland","Northern Europe","Scandinavia","Western Europe"}));
        Main.territories.put("Iceland", new Territory("Iceland", "Europe",
                new String[]{"Great Britain","Greenland","Scandinavia"}));
        Main.territories.put("Northern Europe", new Territory("Northern Europe", "Europe",
                new String[]{"Great Britain","Scandinavia","Southern Europe","Ukraine","Western Europe"}));
        Main.territories.put("Scandinavia", new Territory("Scandinavia", "Europe",
                new String[]{"Great Britain","Iceland","Northern Europe","Ukraine"}));
        Main.territories.put("Southern Europe", new Territory("Southern Europe", "Europe",
                new String[]{"Egypt","Middle East","North Africa","Northern Europe","Ukraine","Western Europe"}));
        Main.territories.put("Ukraine", new Territory("Ukraine", "Europe",
                new String[]{"Afghanistan","Middle East","Northern Europe","Scandinavia","Southern Europe","Ural"}));
        Main.territories.put("Western Europe", new Territory("Western Europe", "Europe",
                new String[]{"Great Britain","North Africa","Northern Europe","Southern Europe"}));

        // Africa
        Main.territories.put("Congo", new Territory("Congo", "Africa",
                new String[]{"East Africa","North Africa","South Africa"}));
        Main.territories.put("East Africa", new Territory("East Africa", "Africa",
                new String[]{"Congo","Egypt","Madagascar","Middle East","North Africa","South Africa"}));
        Main.territories.put("Egypt", new Territory("Egypt", "Africa",
                new String[]{"East Africa","Middle East","North Africa","Southern Europe"}));
        Main.territories.put("Madagascar", new Territory("Madagascar", "Africa",
                new String[]{"East Africa","South Africa"}));
        Main.territories.put("North Africa", new Territory("North Africa", "Africa",
                new String[]{"Brazil","Congo","East Africa","Egypt","Southern Europe","Western Europe"}));
        Main.territories.put("South Africa", new Territory("South Africa", "Africa",
                new String[]{"Congo","East Africa","Madagascar"}));

        // Asia
        Main.territories.put("Afghanistan", new Territory("Afghanistan", "Asia",
                new String[]{"China","India","Middle East","Ukraine","Ural"}));
        Main.territories.put("China", new Territory("China", "Asia",
                new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"}));
        Main.territories.put("India", new Territory("India", "Asia",
                new String[]{"Afghanistan","China","Middle East","Siam"}));
        Main.territories.put("Irkutsk", new Territory("Irkutsk", "Asia",
                new String[]{"Kamchatka","Mongolia","Siberia","Yakutsk"}));
        Main.territories.put("Japan", new Territory("Japan", "Asia",
                new String[]{"Kamchatka","Mongolia"}));
        Main.territories.put("Kamchatka", new Territory("Kamchatka", "Asia",
                new String[]{"Alaska","Irkutsk","Japan","Mongolia","Yakutsk"}));
        Main.territories.put("Middle East", new Territory("Middle East", "Asia",
                new String[]{"Afghanistan","East Africa","Egypt","India","Southern Europe","Ukraine"}));
        Main.territories.put("Mongolia", new Territory("Mongolia", "Asia",
                new String[]{"China","Irkutsk","Japan","Kamchatka","Siberia"}));
        Main.territories.put("Siam", new Territory("Siam", "Asia",
                new String[]{"China","India","Indonesia"}));
        Main.territories.put("Siberia", new Territory("Siberia", "Asia",
                new String[]{"China","Irkutsk","Mongolia","Ural","Yakutsk"}));
        Main.territories.put("Ural", new Territory("Ural", "Asia",
                new String[]{"Afghanistan","China","Siberia","Ukraine"}));
        Main.territories.put("Yakutsk", new Territory("Yakutsk", "Asia",
                new String[]{"Irkutsk","Kamchatka","Siberia"}));

        // Australia
        Main.territories.put("Eastern Australia", new Territory("Eastern Australia", "Australia",
                new String[]{"New Guinea","Western Australia"}));
        Main.territories.put("Indonesia", new Territory("Indonesia", "Australia",
                new String[]{"New Guinea","Siam","Western Australia"}));
        Main.territories.put("New Guinea", new Territory("New Guinea", "Australia",
                new String[]{"Eastern Australia","Indonesia","Western Australia"}));
        Main.territories.put("Western Australia", new Territory("Western Australia", "Australia",
                new String[]{"Eastern Australia","Indonesia","New Guinea"}));
    }

    // Creates all the cards & place into Main.deck object
    // Types = INFANTRY, CAVALRY, ARTILLERY, WILD
    // 42 cards with Main.territories + 2 wild cards = 44 cards
    private void createCards() {        
        // North America: 9
        Main.deck.addCards(new Card(Type.INFANTRY, "Alaska"));
        Main.deck.addCards(new Card(Type.CAVALRY, "Alberta"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "Central America"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "Eastern United States"));
        Main.deck.addCards(new Card(Type.CAVALRY, "Greenland"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "North West Territory"));
        Main.deck.addCards(new Card(Type.CAVALRY, "Ontario"));
        Main.deck.addCards(new Card(Type.INFANTRY, "Quebec"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "Western United States"));

        // South America: 4
        Main.deck.addCards(new Card(Type.INFANTRY, "Argentina"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "Brazil"));
        Main.deck.addCards(new Card(Type.INFANTRY, "Peru"));
        Main.deck.addCards(new Card(Type.INFANTRY, "Venezuela"));

        // Europe: 7
        Main.deck.addCards(new Card(Type.ARTILLERY, "Great Britain"));
        Main.deck.addCards(new Card(Type.INFANTRY, "Iceland"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "Northern Europe"));
        Main.deck.addCards(new Card(Type.CAVALRY, "Scandinavia"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "Southern Europe"));
        Main.deck.addCards(new Card(Type.INFANTRY, "Ukraine"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "Western Europe"));

        // Africa: 6
        Main.deck.addCards(new Card(Type.INFANTRY, "Congo"));
        Main.deck.addCards(new Card(Type.INFANTRY, "East Africa"));
        Main.deck.addCards(new Card(Type.INFANTRY, "Egypt"));
        Main.deck.addCards(new Card(Type.CAVALRY, "Madagascar"));
        Main.deck.addCards(new Card(Type.CAVALRY, "North Africa"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "South Africa"));

        // Asia: 12
        Main.deck.addCards(new Card(Type.CAVALRY, "Afghanistan"));
        Main.deck.addCards(new Card(Type.INFANTRY, "China"));
        Main.deck.addCards(new Card(Type.CAVALRY, "India"));
        Main.deck.addCards(new Card(Type.CAVALRY, "Irkutsk"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "Japan"));
        Main.deck.addCards(new Card(Type.INFANTRY, "Kamchatka"));        
        Main.deck.addCards(new Card(Type.INFANTRY, "Middle East"));
        Main.deck.addCards(new Card(Type.INFANTRY, "Mongolia"));
        Main.deck.addCards(new Card(Type.INFANTRY, "Siam"));
        Main.deck.addCards(new Card(Type.CAVALRY, "Siberia"));
        Main.deck.addCards(new Card(Type.CAVALRY, "Ural"));
        Main.deck.addCards(new Card(Type.CAVALRY, "Yakutsk"));

        // Australia: 4
        Main.deck.addCards(new Card(Type.ARTILLERY, "Eastern Australia"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "Indonesia"));
        Main.deck.addCards(new Card(Type.INFANTRY, "New Guinea"));
        Main.deck.addCards(new Card(Type.ARTILLERY, "Western Australia"));

        // WILD CARDS: 2
        Main.deck.addCards(new Card(Type.WILD, ""));
        Main.deck.addCards(new Card(Type.WILD, ""));
    }
}
