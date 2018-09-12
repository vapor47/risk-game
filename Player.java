import java.util.Scanner;

public class Player 
{
    private static int cardValue = 0; //keeps track of all cards traded in
    
    //Note: I dont know what kind of value we are using for territories here, int is just a place holder
    private int territories[] = new int[42]; //Keeps track of the terrirtories that the player controls
    private boolean continents[] = new boolean[6]; //Keeps track of any continents that have been captured
    
    private String playerName;
    
    public Card hand[] = new Card[6]; //The players currnet cards
    
    private byte territoryCount;
    private byte continentCount;
    private byte cardCount; //The current number of cards the pplayer holds
    
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
    
    public boolean validPlay(Card cardA, Card cardB, Card cardC) //checks weather selected cards are a valid set
    {
        if(cardA.getType() == cardB.getType())
        {
            if(cardA.getType() == cardC.getType())
            {
               return true;
            }
        }
        
        else if(cardA.getType() != cardC.getType() && cardB.getType() != cardC.getType())
        {
            return true;
        }
        
        return false;
    }
    private void useCards() //Function will spend the cards
    {
        updateCardValue();
        /*add to the bumber of troops here*/
    }
    
    public void playHand()
    {
        if(cardCount >=3 ) 
        {
            Scanner input = new Scanner(System.in);
            
            System.out.println("Select your cards:");
            viewHand();
            
            Card play[] = new Card[3];
            
            for(int i = 0, card = -1; i < 3 && card != 0;) //grabs the card number from the user and then determines if the selected cards are playable
            {
                card = input.nextInt();
                if(card <= cardCount && card >=1) 
                    play[i++] = hand[card-1];
                else
                {
                    System.out.println("Please give a valid card number");
                    viewHand();
                    System.out.println("0 for exit");
                }
                
                if(i == 2 && validPlay(play[0], play[1], play[2]))
                {
                    useCards();
                }
            }
        }
        else
            System.out.println("You dont have enough cards to play your hand");
    }
    
    
    public void startTurn()
    {
        
    }
}
