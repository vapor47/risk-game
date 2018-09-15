import java.util.Scanner;
import java.util.Arrays;

public class Player 
{
    private static int cardValue = 0; //keeps track of all cards traded in
    private static Deck deck; //The deck of cards shared among players
    //Note: I dont know what kind of value we are using for territories here, int is just a place holder
    private String territories[] = new String[42]; //Keeps track of the terrirtories that the player controls
    private boolean continents[] = new boolean[6]; //Keeps track of any continents that have been captured
    
    private String playerName;
    
    public Card hand[] = new Card[6]; //The players currnet cards
    
    private byte territoryCount;
    private byte continentCount;
    private byte cardCount; //The current number of cards the player holds
    private int placeableInfantry;
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getName()
    {
            return playerName;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void viewHand() //Prints the current cards in the players hand
    {
        String printFormat = "%s%n%-11s%s%n%-11s%s%n%n"; //The format that will be used to print the card information
        
        System.out.println("Cards Currently In Hand:");
        for(int i = 0; i < cardCount; i++)
            System.out.printf(printFormat, ("Card" + (i+1)), "Territory:", hand[i].getTerritory(), "Army:", hand[i].getType());
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private int territoryValue(int cardIndex[])//returns a number of troops if the card is the same as a territory you own
    {
        int newInfantry = 0;
        int someValue = 1;
        for(int i = 0; i < territoryCount; territoryCount++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(hand[cardIndex[j]].getTerritory().equals(territories[i]))
                    newInfantry += someValue;
            }
        }
        return newInfantry;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean validPlay(Card cardA, Card cardB, Card cardC) //checks weather selected cards are a valid set
    {
        if(cardA.getType() == cardB.getType() && cardA.getType() == cardC.getType())
            return true;
                 
        else if(cardA.getType() != cardC.getType() && cardB.getType() != cardC.getType())
            return true;
        
        return false;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void useCards(int cardIndex[]) //Function will calculate number of infantry from cards
    {
        updateCardValue();
        placeableInfantry += cardValue;
        placeableInfantry += territoryValue(cardIndex);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void calculateInfantry()
    {
        
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void claimTerritory(Territory t)
    {
        t.setOwner(playerName);
        territories[++territoryCount] = t.getTerritoryName();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void loseTerritory(String t)
    {
        for(int i = 0; i < (territoryCount-1); i++)
        {
            if(t.equals(territories[i]))
            {
                while(i < territoryCount)
                {
                    territories[i] = territories[i+1];
                }
            }
        }
        territories[territoryCount--] = null;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void moveInArmies(Territory from, Territory to)
    {
        Scanner input = new Scanner(System.in);
        int armiesMoving;
                
        System.out.printf("How many armies would you like to add (1 - %d)%n", from.getNumArmies()-1);//must keep atleast one army on a territory
        for(armiesMoving = input.nextInt(); armiesMoving > (from.getNumArmies()-1) || armiesMoving < 1; armiesMoving = input.nextInt())
            System.out.printf("Please give a valid number of movable armies (1 - %d)%n", from.getNumArmies()-1);
        
        from.removeArmy(armiesMoving);
        to.addArmy(armiesMoving);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void attack(Territory Attacker, Territory Defender, Player DefendingPlayer)
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
                Defender.removeArmy();
            else
                Attacker.removeArmy();
        }
        if(Defender.getNumArmies() == 0)
        {
            claimTerritory(Defender);
            DefendingPlayer.loseTerritory(Defender.getTerritoryName());
            System.out.printf("Congratulations player %s, you have conquered %s!%n", Attacker.getOwner(), Defender.getOwner());
            moveInArmies(Attacker, Defender);
        }
    }
}