import java.util.Scanner;
import java.util.Arrays;
import risk.TerritoryList;

public class Player 
{
    private static int cardValue = 0; //keeps track of all cards traded in
    private static Deck deck; //The deck of cards shared among players
    //Note: I dont know what kind of value we are using for territories here, int is just a place holder
    TerritoryList territories = new TerritoryList();
    
    private String playerName;
    
    public Card hand[] = new Card[6]; //The players currnet cards
   
    private byte cardCount; //The current number of cards the player holds
    private int placeableInfantry;
    
    Player(String name)
    {
        cardCount = 0;
        placeableInfantry = 0;
        playerName = name;
    }

    int getPlaceableInfantry()
    {
        return placeableInfantry;
    }
    void printOwnedTerritories()
    {
        
    }
    public String getPlayerName() 
    {
        return playerName;
    }
    public void updatePlaceableInfantry(int infantry) 
    {
        placeableInfantry += infantry;
    }
    public void viewHand() //Prints the current cards in the players hand
    {
        String printFormat = "%s%n%-11s%s%n%-11s%s%n%n"; //The format that will be used to print the card information
        
        System.out.println("Cards Currently In Hand:");
        for(int i = 0; i < cardCount; i++)
            System.out.printf(printFormat, ("Card" + (i+1)), "Territory:", hand[i].getTerritory(), "Army:", hand[i].getType());
    }

    private void updateCardValue() //used to calculate the amount of troops awarded for cards turned in
    {
        if(cardValue == 0)
            cardValue = 4;
        else if(cardValue < 12)
           cardValue+=2;
        else if(cardValue == 12)
           cardValue=15;
        else
           cardValue+=5;
    }

    private int CardTerritoryValue(int cardIndex[])//returns a number of troops if the card is the same as a territory you own
    {
        for(int i = 0; i < 3; i++)
        {
            if(territories.hasTerriotory(hand[cardIndex[i]].getTerritory()))
                return 2;
        }
        return 0;
    }

    public boolean validPlay(Card cardA, Card cardB, Card cardC) //checks weather selected cards are a valid set
    {
        if(cardA.getType() == cardB.getType() && cardA.getType() == cardC.getType())
            return true;
                 
        else if(cardA.getType() != cardC.getType() && cardB.getType() != cardC.getType())
            return true;
        
        return false;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void useCards(int cardIndex[]) //Function will calculate number of infantry from cards
    {
        Scanner input = new Scanner(System.in); 
        int v = CardTerritoryValue(cardIndex);
        
        int numSpecial = 0;
        int specialCards[] = new int[3];
        for(int i = 0; i < 3; i++)
        {
            if(territories.hasTerriotory(hand[cardIndex[i]].getTerritory()))
            {
                specialCards[numSpecial] = cardIndex[i];
                numSpecial++;
            }
        }
        
        int tIndex;
        System.out.printf("Chose which terrirtory you would like to place two extra armies on(1=%d)%n", numSpecial);
        printSpecialCards(specialCards, numSpecial);
        for(tIndex = input.nextInt(); tIndex <= 0 || tIndex > numSpecial; tIndex = input.nextInt())
        {
            System.out.printf("Please give a valid number(0-%d)%n", numSpecial);
            printSpecialCards(specialCards, numSpecial);
        }
        placeInfantry(hand[specialCards[tIndex]].getTerritory() ,2);
        updateCardValue();
        unspecificArmyPlacement();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private void unspecificArmyPlacement()
    {
        Scanner input = new Scanner(System.in); 
        int troops = cardValue;
        int pickedTroops;
        String ter;
        System.out.printf("You have %d troops, where would you like to place them?(type territory name)%n", troops);
        territories.printConts();
        while(troops > 0)
        {
            for(ter = input.next(); territories.hasTerriotory(ter); ter = input.next())
                System.out.println("Please give a correct continent number(type territory name)");
            System.out.printf("You have %d troops, please give the number of troops you would like to place on %s",troops, ter);
            for(pickedTroops = input.nextInt(); pickedTroops <= 0 || pickedTroops > troops; pickedTroops = input.nextInt())
                System.out.printf("You have %d troops, please give a valid number of troops you would like to place", troops);
            placeInfantry(ter, pickedTroops);
            troops -= pickedTroops;
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private void printSpecialCards(int specialCards[], int numSpecial)
    {
        for(int i = 0; i < numSpecial; i++)
            System.out.printf("Card %d: %s%n", i, hand[i].getTerritory());
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void playHand()
    {
        if(cardCount >=3 ) 
        {
            Scanner input = new Scanner(System.in);
    
            int cardIndex[] = new int[3];
            
            viewHand();
            
            for(int i = 0; i < 3 && cardIndex[i] != 0; i++) //grabs the card number from the user and then determines if the selected cards are playable
            {   
                System.out.println("Select card " + (i+1) + ":");
                
                for(cardIndex[i] = input.nextInt(); cardIndex[i] > cardCount || cardIndex[i] < 0; cardIndex[i] = input.nextInt()) 
                {
                    System.out.println("Please give a valid card number");
                    viewHand();
                    System.out.println("0 for exit");
                }
                if (cardIndex[i] == 0)
                    return;
            }
            
            useCards(cardIndex);
        }
        else
            System.out.println("You dont have enough cards to play your hand");
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void placeInfantry(String t, int inf)
    {
        Main.territories.get(t).incrementArmies(inf);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void claimTerritory(Territory t)
    {
        Main.territories.get(t.getOwner()).setOwner(Main.playerMap.get(playerName));
        territories.addTerritory(t.getContinent(),t.getTerritoryName());
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void loseTerritory(Territory t)
    {
        territories.removeTerritory(t.getContinent(),t.getTerritoryName());
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static int[] orderedRolls(int arraySize)
    {
        Die die = new Die();
    
        int arr[] = new int[arraySize];
        for(int i = 0, temp = die.roll(); i < arraySize; i++, temp = die.roll())
        {   
            
            int j;
            int k;
            for(j = 0; j < i; j++)
            {
                if(temp > arr[j])
                    break;
            }
            
            for(k = i-1; k >= j; k--)
                arr[k+1] = arr[k];
            
            arr[j] = temp;
        }    

        return arr;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void moveInArmies(Territory from, Territory to)
    {
        Scanner input = new Scanner(System.in);
        int armiesMoving;
                
        System.out.printf("How many armies would you like to add (1 - %d)%n", from.getNumArmies()-1);//must keep atleast one army on a territory
        for(armiesMoving = input.nextInt(); armiesMoving > (from.getNumArmies()-1) || armiesMoving < 1; armiesMoving = input.nextInt())
            System.out.printf("Please give a valid number of movable armies (1 - %d)%n", from.getNumArmies()-1);
        
        Main.territories.get(from.getTerritoryName()).decrementArmies(armiesMoving);
        Main.territories.get(to.getTerritoryName()).incrementArmies(armiesMoving);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void attack(Territory Attacker, Territory Defender)
    {   
        Scanner input = new Scanner(System.in);
        
        int max = 0;
        
        switch(Attacker.getNumArmies())
        {
            case 1: 
                System.out.printf("You need atleast 2 armies to attack");
                return;
            case 2:
                max = 1;
                break;
            case 3:
                max = 2;
                break;
            default:
                max = 3;
                break;
        }
        
        int attackerRolls = 0;
        System.out.printf("Attacker %s, How many troops would you like to use? (1 - %d)%n", Attacker.getOwner(), max);
        for(attackerRolls = input.nextInt(); attackerRolls > max || attackerRolls < 1; attackerRolls = input.nextInt())
            System.out.printf("Please select a valid number of troops (1 - %d)", max);
        
        max = 0;
        if(Defender.getNumArmies() == 1)
            max = 1;
        else
            max = 2;
        
        int defenderRolls = 0;
        System.out.printf("Defender %s, How many troops would you like to use? (1 - %d)%n", Defender.getOwner(), max);
        for(defenderRolls = input.nextInt(); defenderRolls > max || defenderRolls < 1; defenderRolls = input.nextInt())
             System.out.printf("Please select a valid number of troops (1 - %d)", max);
        
        int attackerTroops[] = orderedRolls(attackerRolls);
        int defenderTroops[] = orderedRolls(defenderRolls);
        
        int size = 0;
        if(attackerRolls >= defenderRolls)
            size = defenderRolls;
        else
            size = attackerRolls;
        
        for(int i = 0; Defender.getNumArmies() > 0 && i < size; i++)
        {
            if(attackerTroops[i] > defenderTroops[i])
                Main.territories.get(Defender.getTerritoryName()).decrementArmies(1);
            else
                Main.territories.get(Attacker.getTerritoryName()).decrementArmies(1);
        }
        if(Defender.getNumArmies() == 0)
        {
            claimTerritory(Defender);
            Main.playerMap.get(Defender.getOwner()).loseTerritory(Defender);
            System.out.printf("Congratulations player %s, you have conquered %s!%n", Attacker.getOwner(), Defender.getOwner());
            moveInArmies(Attacker, Defender);
        }
    }
}