public class Territory {

    private String name;
    private String continent;
    private int numArmies;
    private Player owner = new Player("Neutral");
    private String[] adjacentTerritories;

    Territory(String name, String continent, String[] adjacentTerritories){
        this.name = name;
        this.continent = continent;
        numArmies = 0;
        this.adjacentTerritories = adjacentTerritories;
    }

    // For testing purposes
    Territory(String name, String continent, int numArmies, Player owner, String[] adjacentTerritories){
        this.name = name;
        this.continent = continent;
        this.numArmies = numArmies;
        this.adjacentTerritories = adjacentTerritories;
        this.owner = owner;
    }

    public void incrementArmies(int increment){
        numArmies += increment;
    }
    public void decrementArmies(int increment){
        numArmies -= increment;
    }
    public void setOwner(Player owner){
        this.owner = owner;
    }
    String getTerritoryName(){return name;}
    String getContinent(){return continent;}
    int getNumArmies(){return numArmies;}
    Player getOwner(){return owner;}
    public String[] getAdjacentTerritories(){return adjacentTerritories;}
    public void printAdjacentTerritories(){
        for (String x : adjacentTerritories) {
            System.out.print(x + " | ");
        }
        System.out.println("\n------------------------------------");
    }
    public void listTerritoryInfo(){
//        cleaner format testing
//        System.out.println("-----------------------------------------------------------------------------------------------");
//        System.out.printf("%13s %24s %15s %15s %22s\n","Name","Continent","Owner","# of Armies","Adjacent Territories");
//        System.out.printf("%13s %24s %15s %15d\n", name, continent, owner.getPlayerName(), numArmies);
//        System.out.println("-----------------------------------------------------------------------------------------------\n");
        System.out.println(getTerritoryName()+":");
        System.out.println("\tOwner: " + getOwner().getPlayerName());
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


    Eastern United States   North America /max player length/
     */
}
