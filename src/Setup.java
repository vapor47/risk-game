import java.util.HashMap;
import java.util.Scanner;

public class Setup {

    private int numPlayers = 0;
    public final HashMap<String,Territory> territories = new HashMap();
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
        giveStartArmies(numPlayers);
        //createPlayers();
        if(numPlayers == 2){
            // special rules
        }
        else{
            // normal rules
        }

        territories.get("Great Britain").listTerritoryInfo();
    }
    private void createPlayers(int numPlayers){
        playerList = new Player[numPlayers];
        for(int i = 0; i < numPlayers; i++){
//            playerList[i] = Player();
        }
    }
    private void giveStartArmies(int numPlayers){
        switch (numPlayers){
            case 2:
                // 40 infantry
                break;
            case 3:
                // 35 infantry
                break;
            case 4:
                // 30 infantry
                break;
            case 5:
                // 25 infantry
                break;
            case 6:
                // 20 infantry
                break;
            default:
                break;
        }
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
}
