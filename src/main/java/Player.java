import java.util.Scanner;
public class Player implements TerritoryObserver
{
    private static int cardValue = 0; //keeps track of all cards traded in    
    
    TerritoryList territories = new TerritoryList();
    
    //private boolean claimCheck = false; 
    private int claimCheck = 0; 
    
    Credits credits = new Credits();
    
    private String playerName;
    
    public Card hand[] = new Card[11]; //The players current cards
   
    private byte cardCount; //The current number of cards the player holds
    private int placeableInfantry;

    public int territoriesConqueredThisTurn = 0;
    private boolean isActive = false;

    Player(String name)
    {
        isActive = true;
        cardCount = 0;
        placeableInfantry = 0;
        playerName = name;
        Bank bank = new Bank();
        bank.addAccount(name);
//      index = Main.playerList.indexOf(playerName);
    }

    @Override
    public void update(boolean isUnderAttack, String territoryName) {
        if (isUnderAttack) {
            System.out.printf("- %s: Your territory \'%s\', is under attack!\n\n", this.getPlayerName(), territoryName);
        } 
    }
    
    public int getCardCount() {
        return cardCount;
    }
        
    public boolean getActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }    
    public int getNumTerritoriesClaimed() {
        return claimCheck;
    }
    public void updateTerritoriesClaimed(int number) {
        claimCheck += number;
    }
    public TerritoryList getTerritoryList() {
        return territories;
    }
    public void setTerritoryList(TerritoryList territories) {
        this.territories = territories;        
    } 
            
    public void printOwnedTerritories()
    {
        territories.printConts();
        System.out.println();
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
            System.out.printf(printFormat, ("Card " + (i+1)), "Territory:", hand[i].getTerritoryName(), "Army:", hand[i].getType());
    }

    protected void updateCardValue() //used to calculate the amount of troops awarded for cards turned in
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
    public int territoriesConquered()
    {
    	return territories.getTerritoryCount();
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int calculateInfantry(Deck deck)
    {
        int inf = playHand(deck);
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

        else if(cardA.getType() == Type.WILD || cardB.getType() == Type.WILD || cardC.getType() == Type.WILD)
            return true;

        return false;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addCards(Card card) {
        hand[cardCount++] = card;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void useCards(int cardIndex[]) //Function will calculate number of infantry from cards
    {
        Scanner input = new Scanner(System.in); 
        
        int numSpecial = 0;
        int specialCards[] = new int[3];
        for(int i = 0; i < 3; i++)
        {
            if(territories.hasTerritory(hand[cardIndex[i]].getTerritory()))
            {
                specialCards[numSpecial] = cardIndex[i];
                numSpecial++;
            }
        }
        
        //input.close();
        
        if(numSpecial > 0)
        {
            int tIndex;
            System.out.printf("Chose which territory you would like to place two extra armies on(1-%d)%n", numSpecial);
            printSpecialCards(specialCards, numSpecial);
            for(tIndex = input.nextInt(); tIndex <= 0 || tIndex > numSpecial; tIndex = input.nextInt())
            {
                System.out.printf("Please give a valid number(1-%d)%n", numSpecial);
                printSpecialCards(specialCards, numSpecial);
            }
            placeInfantry(hand[specialCards[tIndex + 1]].getTerritory() ,2);
        }
        updateCardValue();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void discard(int cardIndex[], Deck deck)
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
                replacement[j++] = hand[i];
            } 
        }
        hand = replacement;
        cardCount-=3;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private void printSpecialCards(int specialCards[], int numSpecial)
    {
        for(int i = 0; i < numSpecial; i++)
            System.out.printf("Card %d: %s%n", i + 1, hand[i].getTerritory());
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int playHand(Deck deck)
    {
        if(cardCount >=3) 
        {
            Scanner input = new Scanner(System.in);
    
            int cardIndex[] = new int[3];
            
            viewHand();
            
            for(int i = 0; i < 3 && cardIndex[i] != -1; i++) //grabs the card number from the user and then determines if the selected cards are playable
            {   
                System.out.println("Select card " + (i+1) + ":");
                
                for(cardIndex[i] = input.nextInt(); cardIndex[i] > cardCount || cardIndex[i] < 0; cardIndex[i] = input.nextInt()) 
                {
                    System.out.println("Please give a valid card number");
                    viewHand();
                    System.out.println("-1 for exit");
                }
                
                //input.close();
                
                if (cardIndex[i] == -1)
                    return 0;
            }
            useCards(cardIndex);
            discard(cardIndex, deck);
            return cardValue;
        }
        
        System.out.println("You dont have enough cards to play your hand");
        return 0;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void placeInfantry(Territory territory, int inf)
    {
        territory.incrementArmies(inf);
        placeableInfantry -= inf;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void placeInfantry(String territory, int inf)
    {
        Main.territories.get(territory).incrementArmies(inf);
        placeableInfantry -= inf;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void claimTerritory(Territory t)
    {
        t.setOwner(this);
        territories.addTerritory(t);
        territoriesConqueredThisTurn++;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void loseTerritory(Territory t)
    {
        territories.removeTerritory(t);
        t.setOwner(new Player("Neutral"));
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
    public int moveInArmies(Territory from, Territory to)
    {
    	Replay replay = new Replay();
    	
        Scanner input = new Scanner(System.in);
        int armiesMoving;
                
        System.out.printf("How many armies would you like to add (1 - %d)%n", from.getNumArmies()-1);//must keep atleast one army on a territory
        for(armiesMoving = input.nextInt(); armiesMoving > (from.getNumArmies()-1) || armiesMoving < 1; armiesMoving = input.nextInt())
            System.out.printf("Please give a valid number of movable armies (1 - %d)%n", from.getNumArmies()-1);
        
        //input.close();
	    
        from.decrementArmies(armiesMoving);
        to.incrementArmies(armiesMoving);
        
        replay.update(to.getNumArmies() + "troops moved from " + from.getTerritoryName().toString() + "to" + to.getTerritoryName().toString() + "\n");
   		
   	return armiesMoving;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void drawCards(Deck deck)
    {
    	Replay replay = new Replay();
        if(claimCheck > 0)
        {
            hand[cardCount++] = deck.draw();
            System.out.printf("%s has drawn a card, Territory: %s, Army: %s%n", playerName,hand[cardCount-1].getTerritory(), hand[cardCount-1].getType());
            replay.update("Card Draw: Territory: " +  hand[cardCount-1].getTerritory() + ", Army: " + hand[cardCount-1].getType());
            claimCheck = 0;
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
private int canAttack(Territory Attacker)
{
	switch(Attacker.getNumArmies())
    {
        case 1: 
            System.out.printf("You need atleast 2 armies to attack");
            return 0;
        case 2:
        	return 1;
        case 3:
            return 2;
        default:
            return 3;
    }
}
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean attack(Territory Attacker, Territory Defender)
    {   
    	Player defender = Defender.getOwner();

    	Replay replay = new Replay();
    	
        Scanner input = new Scanner(System.in);
        
        int attackerTroopsLost = 0;
        int defenderTroopsLost = 0;

        int max = canAttack(Attacker);
        if(max == 0)
    	{
        	input.close();
        	return false;	
    	}
        	
        Defender.updateUnderAttackStatus(true);                    
        Defender.notifyObservers();

        int attackerRolls = 0;
        System.out.printf("Attacker %s, How many troops would you like to use? (1 - %d)%n", Attacker.getOwner().getPlayerName(), max);
        for(attackerRolls = input.nextInt(); attackerRolls > max || attackerRolls < 1; attackerRolls = input.nextInt())
            System.out.printf("Please select a valid number of troops (1 - %d)", max);
        
        max = 0;
        if(Defender.getNumArmies() == 1)
            max = 1;
        else
            max = 2;
        
        int defenderRolls = 0;
        System.out.printf("Defender %s, How many troops would you like to use? (1 - %d)%n", Defender.getOwner().getPlayerName(), max);
        for(defenderRolls = input.nextInt(); defenderRolls > max || defenderRolls < 1; defenderRolls = input.nextInt())
             System.out.printf("Please select a valid number of troops (1 - %d)", max);
        
        input.close();
        
        int attackerTroops[] = orderedRolls(attackerRolls);
        int defenderTroops[] = orderedRolls(defenderRolls);
        
        int size = 0;
        if(attackerRolls >= defenderRolls)
            size = defenderRolls;
        else
            size = attackerRolls;
        
        for(int i = 0; Defender.getNumArmies() > 0 && i < size; i++)
        {
            if(attackerTroops[i] > defenderTroops[i]) {
                Defender.decrementArmies(1);
            	defenderTroopsLost++;  
            }
            else {
                Attacker.decrementArmies(1);
            	attackerTroopsLost++;
            }
        }
        
        replay.update("Results:\n" + 
        Attacker.getTerritoryName().toString() + ": " + Attacker.getNumArmies() + "\n" + 
        Defender.getTerritoryName().toString() + ": " + Defender.getNumArmies() + "\n"); 
        
        
        if(Defender.getNumArmies() == 0)
        {
            Defender.getOwner().loseTerritory(Defender);
            claimTerritory(Defender);
            System.out.printf("Congratulations player %s, you have conquered %s!%n", Attacker.getOwner().getPlayerName(), Defender.getTerritoryName().toString());
            Defender.removeObserver();
            Defender.addObserver(Attacker.getOwner());
            moveInArmies(Attacker, Defender);
            replay.update(Defender.getTerritoryName().toString() + "Has been conquered\n");
            claimCheck++;
        }
        
        System.out.printf("Attacker %s lost %d troops\n", Attacker.getOwner().getPlayerName(), attackerTroopsLost);
        System.out.printf("Defender %s lost %d troops\n", defender.getPlayerName(), defenderTroopsLost);    // In case we conquer the territory, display old owner    
        
       	if (defender.territories.getTerritoryCount() <= 0) {
            // Player's outta the game            
            defender.isActive = false;
            System.out.printf("- %s has been defeated!\n", defender.getPlayerName());
            return true;
        }

        Defender.updateUnderAttackStatus(false);    
        
        return false;
    }
}