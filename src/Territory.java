public class Territory {

    private String name;
    private String continent;
    private int numArmies;
    private String owner;
    private String[] adjacentTerritories;

    Territory(String name, String continent, String[] adjacentTerritories){
        this.name = name;
        this.continent = continent;
        numArmies = 0;
        owner = "Neutral";
        this.adjacentTerritories = adjacentTerritories;
    }

    Territory(String name, String continent, int numArmies, String owner, String[] adjacentTerritories){
        this.name = name;
        this.continent = continent;
        this.numArmies = numArmies;
        this.owner = owner;
        this.adjacentTerritories = adjacentTerritories;
    }

    public String getTerritoryName(){return name;}
    public String getContinent(){return continent;}
    public int getNumArmies(){return numArmies;}
    public void removeArmy(){numArmies--;}
    public void removeArmy(int oldArmies){numArmies-=oldArmies;}
    public void addArmy(int newArmies){numArmies+=newArmies;}
    public String getOwner(){return owner;}
    public void setOwner(String owner){this.owner = owner;}
    public String[] getAdjacentTerritories(){return adjacentTerritories;}
    public void printAdjacentTerritories(){
        for (String x : adjacentTerritories) {
            System.out.print(x + " | ");
        }
    }
    public void listTerritoryInfo(){
        System.out.println(getTerritoryName()+":");
        System.out.println("\tOwner: " + getOwner());
        System.out.println("\tNumber of armies: " + getNumArmies());
        System.out.println("\tContinent: " + getContinent());
        System.out.print("\tAdjacent territories: ");
            printAdjacentTerritories();
    }
    /* possible format for later
        Name          Continent           Owner        # of Armies        Adjacent Territories
    Great Britain       Europe           Player 1           0                   Iceland
                                                                             Northern Europe
                                                                              Scandinavia
                                                                              Western Europe
     */
}
