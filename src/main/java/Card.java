public class Card {      
    private Type cardType;    
    private String cardTerritory;
    
    public Card() {
        cardType = null;
        cardTerritory = null;
    }
    
    public Card(Type cardType, String territory) {
        setType(cardType);
        setTerritory(territory);
    }
    
    public void setType(Type cardType) {
        this.cardType = cardType;        
    }
    public Type getType() {
        return cardType;
    }
    public void setTerritory(String territory) {
        cardTerritory = territory;
    }
    public Territory getTerritory() {
        return Main.territories.get(cardTerritory);
    }
     
    @Override
    public String toString() {
        String cardTypeInfo = cardType.toString();
        return cardTerritory + ", " + cardTypeInfo;
    }
}

enum Type{
    INFANTRY, CAVALRY, ARTILLERY, WILD;
}
