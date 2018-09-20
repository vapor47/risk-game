public class Card {      
    private Type cardType;    
    private String cardTerritory;
    
    public Card() {
        cardType = null;
        cardTerritory = "";
    }
    
    public Card(Type cardType, String territoryName) {
        setType(cardType);
        setTerritory(territoryName);
    }
    
    public void setType(Type cardType) {
        this.cardType = cardType;        
    }
    public Type getType() {
        return cardType;
    }
    public void setTerritory(String TerritoryName) {
        cardTerritory = TerritoryName;
    }
    public String getTerritory() {
        return cardTerritory;
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
