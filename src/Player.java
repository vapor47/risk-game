import java.util.Scanner;

public class Player 
{
    private static int cardValue = 0; //keeps track of all cards traded in
    
    //Note: I dont know what kind of value we are using for territories here, int is just a place holder
    private int territories[] = new int[42]; //Keeps track of the terrirtories that the player controls
    private boolean continents[] = new boolean[6]; //Keeps track of any continents that have been captured
    
    public Card hand[] = new Card[6]; //The players currnet cards
    
    private byte territoryCount;
    private byte continentCount;
    private byte cardCount; //The current number of cards the pplayer holds
    
    public void viewHand()
    {
    
    }
    
    private void updateCardValue()
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
    
    public boolean validPlay(Card cardA, Card cardB, Card cardC)
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
    private void useCards()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("");
        
    }
    
    public void playHand()
    {
        if(cardCount >=3 );
             
    }
    
    
    public void startTurn()
    {
        
    }
}
