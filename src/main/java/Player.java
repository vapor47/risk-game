import java.util.Scanner;
import java.util.Arrays;
public class Player 
{
    private static int cardValue = 0; //keeps track of all cards traded in
    private static Deck deck; //The deck of cards shared among players
    //Note: I dont know what kind of value we are using for territories here, int is just a place holder
    TerritoryList territories = new TerritoryList();
    
    private boolean claimCheck = false; 
    
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

    void printOwnedTerritories()
    {
        territories.printConts();
    }
    public String getPlayerName() 
    {
        return playerName;
    }
    public int getPlaceableInfantry()
    {
        return placeableInfantry;
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int calculateInfantry()
    {
        int inf = playHand();
        int bonus = 0;
        if((territories.getTerritoryCount() / 3) < 3)
            bonus = 3;
        else
            bonus = territories.getTerritoryCount() / 3;
        inf+= bonus;
        return inf;       
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        if(numSpecial > 0)
        {
            int tIndex;
            System.out.printf("Chose which terrirtory you would like to place two extra armies on(1=%d)%n", numSpecial);
            printSpecialCards(specialCards, numSpecial);
            for(tIndex = input.nextInt(); tIndex <= 0 || tIndex > numSpecial; tIndex = input.nextInt())
            {
                System.out.printf("Please give a valid number(0-%d)%n", numSpecial);
                printSpecialCards(specialCards, numSpecial);
            }
            placeInfantry(hand[specialCards[tIndex]].getTerritory() ,2);
        }
        updateCardValue();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void discard(int cardIndex[])
    {
        for(int i = 0; i < cardCount; i++)
        {
            deck.discard(hand[cardIndex[i]]);
            hand[cardIndex[i]] = null;
        }
        Card replacement[] = new Card[cardCount - 3];
        for(int i = 0, j = 0; i <  cardCount; i++)
        {
            if(hand[i] != null)
            {
                replacement[j] = hand[i];
            } 
        }
        hand = replacement;
        cardCount-=3;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private void printSpecialCards(int specialCards[], int numSpecial)
    {
        for(int i = 0; i < numSpecial; i++)
            System.out.printf("Card %d: %s%n", i, hand[i].getTerritory());
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int playHand()
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
                    return 0;
            }
            useCards(cardIndex);
            discard(cardIndex);
            return cardValue;
        }
        
        System.out.println("You dont have enough cards to play your hand");
        return 0;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void placeInfantry(String t, int inf)
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
    public void drawCards()
    {
        if(claimCheck)
        {
            hand[cardCount++] = deck.draw();
            System.out.printf("Player %s has drawn a card, Territory: %s, Army: %s%n", playerName,hand[cardCount-1].getTerritory(), hand[cardCount-1].getType());
            claimCheck = false;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        System.out.printf("Attacker %s, How many troops would you like to use? (1 - %d)%n", Attacker.getOwner().getName(), max);
        for(attackerRolls = input.nextInt(); attackerRolls > max || attackerRolls < 1; attackerRolls = input.nextInt())
            System.out.printf("Please select a valid number of troops (1 - %d)", max);
        
        max = 0;
        if(Defender.getNumArmies() == 1)
            max = 1;
        else
            max = 2;
        
        int defenderRolls = 0;
        System.out.printf("Defender %s, How many troops would you like to use? (1 - %d)%n", Defender.getOwner().getName(), max);
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
            System.out.printf("Congratulations %s, you have conquered %s!%n", Attacker.getOwner().getName(), Defender.getOwner().getName());
            claimTerritory(Defender);
<<<<<<< HEAD
            DefendingPlayer.loseTerritory(Defender.getTerritoryName());
=======
            Main.playerMap.get(Defender.getOwner()).loseTerritory(Defender);
            System.out.printf("Congratulations player %s, you have conquered %s!%n", Attacker.getOwner(), Defender.getOwner());
>>>>>>> master
            moveInArmies(Attacker, Defender);
            claimCheck = true;
        }
    }
}