public class Deck {      
    private final int CARDS_IN_DECK = 44;
    private int numCardsInMainDeck, numCardsDiscarded;
    private int numNormalCards = 42;
    private int numWildCards = 2; 
    //private ArrayList<Card> mainDeck = new ArrayList<Card>(CARDS_IN_DECK);
    private ArrayList<Card> mainDeck = new ArrayList<Card>();
    private ArrayList<Card> discardPile = new ArrayList<Card>();
    
    public Deck() {
        numCardsInMainDeck = 0;
        numCardsDiscarded = 0;
    }
    
    //.......Functions........//
    public void addCards(Card newCard) {
        mainDeck.add(newCard);
        numCardsInMainDeck++;
    }
    
    //Pick a random card from deck & remove
    public Card draw() {
        if (mainDeck.size() <= 0) {
            refillDeck();   //Refill using discard pile if Deck empty
        }
        
        System.out.println("Deck size is: " + mainDeck.size());
        int randomDraw = (int)(Math.random() * numCardsInMainDeck); //Random number from 0 to numCards in deck
        Card cardDrawn = mainDeck.get(randomDraw);
        mainDeck.remove(cardDrawn);
        System.out.println("Deck size is now: " + mainDeck.size());        
        
        numCardsInMainDeck--;             
        
        return cardDrawn;
    }
            
    //Place card in discard pile
    public void discard(Card thrownCard) {
        discardPile.add(thrownCard); //Place card in discard pile
        numCardsDiscarded++;
    }        
            
    //copies over discard deck into mainDeck
    private void refillDeck() {
        mainDeck.addAll(discardPile);   //Copies content of discard pile into main deck        
        discardPile.clear();
        
        numCardsDiscarded = discardPile.size();
        numCardsInMainDeck = mainDeck.size();
    }
    
    //Print all cards in Deck
    public void printDeck() {
        for (Card card : mainDeck) {
            System.out.printf("Card: %s\n", card);
        }
    }
}
