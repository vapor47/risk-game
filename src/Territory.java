public class Territory {

    private String name;
    private String continent;
    private int numArmies;
    private int owner;
    private String[] adjacentTerritories;

    Territory(String name, String continent, String[] adjacentTerritories){
        this.name = name;
        this.continent = continent;
        numArmies = 0;
        owner = -1; // -1 == neutral/unowned
        this.adjacentTerritories = adjacentTerritories;
    }

    Territory(String name, String continent, int numArmies, int owner, String[] adjacentTerritories){
        this.name = name;
        this.continent = continent;
        this.numArmies = numArmies;
        this.owner = owner;
        this.adjacentTerritories = adjacentTerritories;
    }

    public String getTerritoryName(){return name;}
    public String getContinent(){return continent;}
    public int getNumArmies(){return numArmies;}
    public int getOwner(){return owner;}
    public String[] getAdjacentTerritories(){return adjacentTerritories;}
}
