import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Setup {

    public int numPlayers;
    private final HashMap<String,Territory> territories = new HashMap();

    private Deck deck = new Deck(); // Creates a deck object 

    private Player[] playerList;

    Setup(){
        Scanner input = new Scanner(System.in);
        System.out.print("Welcome to Risk!\nHow many people are playing(2-6): ");
        numPlayers = input.nextInt();
        while(numPlayers < 2 || numPlayers > 6) {
            System.out.print("Invalid option! Please enter a number from 2-6: ");
            numPlayers = input.nextInt();
        }
        createTerritories();
        createCards();
        createPlayers(numPlayers);
        giveStartingInfantry(numPlayers);

        chooseRandomPlayer(numPlayers);
        if(numPlayers == 2){
            Player neutral = new Player("Neutral");
            twoPlayerStart();
        }
        else{
            // normal rules
        }
        int count = 0;
        for(Map.Entry<String, Territory> x: territories.entrySet()){
            System.out.print(x.getValue().getTerritoryName() + " || ");
            System.out.println(x.getValue().getOwner().getPlayerName());
        }

    }
    /* special rules
            give both players 14 random territories and place 1 infantry on them
                same for neutral
            then:
                go back and forth between both players
                    1. place 2 infantry on any 1 or 2 territories
                        either 1 & 1, or 2 infantry on 1 territory
                    2. place 1 infantry on any neutral territory
    */
    private void twoPlayerStart(){
        // set infantry on all territories to 1
//        for(int i = 0; i < 14; i++){
//            // give player 0 and 1 random territory
//        }
        int i = 0;
        for(Map.Entry<String, Territory> x: territories.entrySet()){
            x.getValue().incrementArmies(1);
            if(i < 28) { // 14 territories for each player
                x.getValue().setOwner(playerList[i++ % 2]);
            }
        }
    }

    // returns number from 0 to (numPlayers - 1)
    private int chooseRandomPlayer(int numPlayers){
        return (int)(Math.random()*numPlayers);
    }
    private void createPlayers(int numPlayers){
        playerList = new Player[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            playerList[i] = new Player("Player " + (i+1));
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
        for(Player x : playerList)
            x.updatePlaceableInfantry(numInfantry);
    }
    private void createTerritories(){
        // North America
        territories.put("Alaska", new Territory("Alaska", "North America",
                new String[]{"Alberta","Kamchatka","North West Territory"}));
        territories.put("Alberta", new Territory("Alberta", "North America",
                new String[]{"Alaska","North West Territory","Ontario","Western United States"}));
        territories.put("Central America", new Territory("Central America", "North America",
                new String[]{"Eastern United States","Venezuela","Western United States"}));
        territories.put("Eastern United States", new Territory("Eastern United States", "North America",
                new String[]{"Central America","Ontario","Quebec","Western United States"}));
        territories.put("Greenland", new Territory("Greenland", "North America",
                new String[]{"Iceland","North West Territory","Ontario","Quebec"}));
        territories.put("North West Territory", new Territory("North West Territory", "North America",
                new String[]{"Alaska","Alberta","Greenland","Ontario"}));
        territories.put("Ontario", new Territory("Ontario", "North America",
                new String[]{"Alberta","Eastern United States","Greenland","North West Territory","Quebec","Western United States"}));
        territories.put("Quebec", new Territory("Quebec", "North America",
                new String[]{"Eastern United States","Greenland","Ontario"}));
        territories.put("Western United States", new Territory("Western United States", "North America",
                new String[]{"Alberta","Central America","Eastern United States","Ontario"}));

        // South America
        territories.put("Argentina", new Territory("Argentina", "South America",
                new String[]{"Brazil","Peru"}));
        territories.put("Brazil", new Territory("Brazil", "South America",
                new String[]{"Argentina","North Africa","Peru","Venezuela"}));
        territories.put("Peru", new Territory("Peru", "South America",
                new String[]{"Argentina","Brazil","Venezuela"}));
        territories.put("Venezuela", new Territory("Venezuela", "South America",
                new String[]{"Brazil","Central America","Peru"}));

        // Europe
        territories.put("Great Britain", new Territory("Great Britain", "Europe",
                new String[]{"Iceland","Northern Europe","Scandinavia","Western Europe"}));
        territories.put("Iceland", new Territory("Iceland", "Europe",
                new String[]{"Great Britain","Greenland","Scandinavia"}));
        territories.put("Northern Europe", new Territory("Northern Europe", "Europe",
                new String[]{"Great Britain","Scandinavia","Southern Europe","Ukraine","Western Europe"}));
        territories.put("Scandinavia", new Territory("Scandinavia", "Europe",
                new String[]{"Great Britain","Iceland","Northern Europe","Ukraine"}));
        territories.put("Southern Europe", new Territory("Southern Europe", "Europe",
                new String[]{"Egypt","Middle East","North Africa","Northern Europe","Ukraine","Western Europe"}));
        territories.put("Ukraine", new Territory("Ukraine", "Europe",
                new String[]{"Afghanistan","Middle East","Northern Europe","Scandinavia","Southern Europe","Ural"}));
        territories.put("Western Europe", new Territory("Western Europe", "Europe",
                new String[]{"Great Britain","North Africa","Northern Europe","Southern Europe"}));

        // Africa
        territories.put("Congo", new Territory("Congo", "Africa",
                new String[]{"East Africa","North Africa","South Africa"}));
        territories.put("East Africa", new Territory("East Africa", "Africa",
                new String[]{"Congo","Egypt","Madagascar","Middle East","North Africa","South Africa"}));
        territories.put("Egypt", new Territory("Egypt", "Africa",
                new String[]{"East Africa","Middle East","North Africa","Southern Europe"}));
        territories.put("Madagascar", new Territory("Madagascar", "Africa",
                new String[]{"East Africa","South Africa"}));
        territories.put("North Africa", new Territory("North Africa", "Africa",
                new String[]{"Brazil","Congo","East Africa","Egypt","Southern Europe","Western Europe"}));
        territories.put("South Africa", new Territory("South Africa", "Africa",
                new String[]{"Congo","East Africa","Madagascar"}));

        // Asia
        territories.put("Afghanistan", new Territory("Afghanistan", "Asia",
                new String[]{"China","India","Middle East","Ukraine","Ural"}));
        territories.put("China", new Territory("China", "Asia",
                new String[]{"Afghanistan","India","Mongolia","Siam","Siberia","Ural"}));
        territories.put("India", new Territory("India", "Asia",
                new String[]{"Afghanistan","China","Middle East","Siam"}));
        territories.put("Irkutsk", new Territory("Irkutsk", "Asia",
                new String[]{"Kamchatka","Mongolia","Siberia","Yakutsk"}));
        territories.put("Japan", new Territory("Japan", "Asia",
                new String[]{"Kamchatka","Mongolia"}));
        territories.put("Kamchatka", new Territory("Kamchatka", "Asia",
                new String[]{"Alaska","Irkutsk","Japan","Mongolia","Yakutsk"}));
        territories.put("Middle East", new Territory("Middle East", "Asia",
                new String[]{"Afghanistan","East Africa","Egypt","India","Southern Europe","Ukraine"}));
        territories.put("Mongolia", new Territory("Mongolia", "Asia",
                new String[]{"China","Irkutsk","Japan","Kamchatka","Siberia"}));
        territories.put("Siam", new Territory("Siam", "Asia",
                new String[]{"China","India","Indonesia"}));
        territories.put("Siberia", new Territory("Siberia", "Asia",
                new String[]{"China","Irkutsk","Mongolia","Ural","Yakutsk"}));
        territories.put("Ural", new Territory("Ural", "Asia",
                new String[]{"Afghanistan","China","Siberia","Ukraine"}));
        territories.put("Yakutsk", new Territory("Yakutsk", "Asia",
                new String[]{"Irkutsk","Kamchatka","Siberia"}));

        // Australia
        territories.put("Eastern Australia", new Territory("Eastern Australia", "Australia",
                new String[]{"New Guinea","Western Australia"}));
        territories.put("Indonesia", new Territory("Indonesia", "Australia",
                new String[]{"New Guinea","Siam","Western Australia"}));
        territories.put("New Guinea", new Territory("New Guinea", "Australia",
                new String[]{"Eastern Australia","Indonesia","Western Australia"}));
        territories.put("Western Australia", new Territory("Western Australia", "Australia",
                new String[]{"Eastern Australia","Indonesia","New Guinea"}));
    }

    // Creates all the cards & place into deck object
    // Types = INFANTRY, CAVALRY, ARTILLERY, WILD
    // 42 cards with territories + 2 wild cards = 44 cards
    private void createCards() {        
        // North America: 9
        deck.addCards(new Card(Type.INFANTRY, "Alaska"));
        deck.addCards(new Card(Type.CAVALRY, "Alberta"));
        deck.addCards(new Card(Type.ARTILLERY, "Central America"));
        deck.addCards(new Card(Type.ARTILLERY, "Eastern United States"));
        deck.addCards(new Card(Type.CAVALRY, "Greenland"));
        deck.addCards(new Card(Type.ARTILLERY, "North West Territory"));
        deck.addCards(new Card(Type.CAVALRY, "Ontario"));
        deck.addCards(new Card(Type.INFANTRY, "Quebec"));
        deck.addCards(new Card(Type.ARTILLERY, "Western United States"));

        // South America: 4
        deck.addCards(new Card(Type.INFANTRY, "Argentina"));
        deck.addCards(new Card(Type.ARTILLERY, "Brazil"));
        deck.addCards(new Card(Type.INFANTRY, "Peru"));
        deck.addCards(new Card(Type.INFANTRY, "Venezuela"));

        // Europe: 7
        deck.addCards(new Card(Type.ARTILLERY, "Great Britain"));
        deck.addCards(new Card(Type.INFANTRY, "Iceland"));
        deck.addCards(new Card(Type.ARTILLERY, "Northern Europe"));
        deck.addCards(new Card(Type.CAVALRY, "Scandinavia"));
        deck.addCards(new Card(Type.ARTILLERY, "Southern Europe"));
        deck.addCards(new Card(Type.INFANTRY, "Ukraine"));
        deck.addCards(new Card(Type.ARTILLERY, "Western Europe"));

        // Africa: 6
        deck.addCards(new Card(Type.INFANTRY, "Congo"));
        deck.addCards(new Card(Type.INFANTRY, "East Africa"));
        deck.addCards(new Card(Type.INFANTRY, "Egypt"));
        deck.addCards(new Card(Type.CAVALRY, "Madagascar"));
        deck.addCards(new Card(Type.CAVALRY, "North Africa"));
        deck.addCards(new Card(Type.ARTILLERY, "South Africa"));

        // Asia: 12
        deck.addCards(new Card(Type.CAVALRY, "Afghanistan"));
        deck.addCards(new Card(Type.INFANTRY, "China"));
        deck.addCards(new Card(Type.CAVALRY, "India"));
        deck.addCards(new Card(Type.CAVALRY, "Irkutsk"));
        deck.addCards(new Card(Type.ARTILLERY, "Japan"));
        deck.addCards(new Card(Type.INFANTRY, "Kamchatka"));        
        deck.addCards(new Card(Type.INFANTRY, "Middle East"));
        deck.addCards(new Card(Type.INFANTRY, "Mongolia"));
        deck.addCards(new Card(Type.INFANTRY, "Siam"));
        deck.addCards(new Card(Type.CAVALRY, "Siberia"));
        deck.addCards(new Card(Type.CAVALRY, "Ural"));
        deck.addCards(new Card(Type.CAVALRY, "Yakutsk"));

        // Australia: 4
        deck.addCards(new Card(Type.ARTILLERY, "Eastern Australia"));
        deck.addCards(new Card(Type.ARTILLERY, "Indonesia"));
        deck.addCards(new Card(Type.INFANTRY, "New Guinea"));
        deck.addCards(new Card(Type.ARTILLERY, "Western Australia"));

        // WILD CARDS: 2
        deck.addCards(new Card(Type.WILD, ""));
        deck.addCards(new Card(Type.WILD, ""));
    }
}
