public class Card {      
    private Type cardType;    
    private Territory cardTerritory;
    
    public Card() {
        cardType = null;
        cardTerritory = null;
    }
    
    public Card(Type cardType, Territory territory) {
        setType(cardType);
        setTerritory(territory);
    }
    
    public void setType(Type cardType) {
        this.cardType = cardType;        
    }
    public Type getType() {
        return cardType;
    }
    public void setTerritory(Territory territory) {
        cardTerritory = territory;
    }
    public Territory getTerritory() {
        return cardTerritory;
    }
     
    @Override
    public String toString() {
        String cardTypeInfo = cardType.toString();
        return cardTerritory.getTerritoryName() + ", " + cardTypeInfo;
    }
}

enum Type{
    INFANTRY, CAVALRY, ARTILLERY, WILD;
}
