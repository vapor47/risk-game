enum Continent {
    NORTH_AMERICA("North America"),
    SOUTH_AMERICA("South America"),
    EUROPE("Europe"),
    AFRICA("Africa"),
    ASIA("Asia"),
    AUSTRALIA("Australia");

    private final String name;

    Continent(String s){
        name = s;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
enum TerritoryName{
    // North America
    ALASKA("Alaska"),
    ALBERTA("Alberta"),
    CENTRAL_AMERICA("Central America"),
    EASTERN_UNITED_STATES("Eastern United States"),
    GREENLAND("Greenland"),
    NORTHWEST_TERRITORY("Northwest Territory"),
    ONTARIO("Ontario"),
    QUEBEC("Quebec"),
    WESTERN_UNITED_STATES("Western United States"),

    // South America
    ARGENTINA("Argentina"),
    BRAZIL("Brazil"),
    PERU("Peru"),
    VENEZUELA("Venezuela"),

    // Europe
    GREAT_BRITAIN("Great Britain"),
    ICELAND("Iceland"),
    NORTHERN_EUROPE("Northern Europe"),
    SCANDINAVIA("Scandinavia"),
    SOUTHERN_EUROPE("Southern Europe"),
    UKRAINE("Ukraine"),
    WESTERN_EUROPE("Western Europe"),

    // Africa
    CONGO("Congo"),
    EAST_AFRICA("East Africa"),
    EGYPT("Egypt"),
    MADAGASCAR("Madagascar"),
    NORTH_AFRICA("North Africa"),
    SOUTH_AFRICA("South Africa"),

    // Asia
    AFGHANISTAN("Afghanistan"),
    CHINA("China"),
    INDIA("India"),
    IRKUTSK("Irkutsk"),
    JAPAN("Japan"),
    KAMCHATKA("Kamchatka"),
    MIDDLE_EAST("Middle East"),
    MONGOLIA("Mongolia"),
    SIAM("Siam"),
    SIBERIA("Siberia"),
    URAL("Ural"),
    YAKUTSK("Yakutsk"),

    // Australia
    EASTERN_AUSTRALIA("Eastern Australia"),
    INDONESIA("Indonesia"),
    NEW_GUINEA("New Guinea"),
    WESTERN_AUSTRALIA("Western Australia");

    private final String name;

    TerritoryName(String s){
        name = s;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}

public class Territory {

    private TerritoryName name;
    private Continent continent;    
    private int numArmies;
    private boolean isUnderAttack;
    private TerritoryObserver playerObserver;   // only need 1 observer per territory 
    private Player owner = new Player("Neutral");
    private String[] adjacentTerritories;

    Territory(TerritoryName name, Continent continent, String[] adjacentTerritories){
        this.name = name;
        this.continent = continent;
        numArmies = 0;
        this.adjacentTerritories = adjacentTerritories;
        playerObserver = owner;
    }

    // For testing purposes
    Territory(TerritoryName name, Continent continent, int numArmies, Player owner, String[] adjacentTerritories){
        this.name = name;
        this.continent = continent;
        this.numArmies = numArmies;
        this.adjacentTerritories = adjacentTerritories;
        this.owner = owner;
        playerObserver = owner;
    }
    
    public void addObserver(TerritoryObserver obs) {        
        playerObserver = obs;
    }
    
    public void removeObserver() {
        playerObserver = null;
    }
    
    public TerritoryObserver getObserver() {
        return playerObserver;
    }
    
    public void updateUnderAttackStatus(boolean isUnderAttack) {
        this.isUnderAttack = isUnderAttack;
    }
    
    public void notifyObservers() {
        if (isUnderAttack) {
            playerObserver.update(isUnderAttack, name.toString());
        }
    }
    
    public boolean isUnderAttack() {
        return isUnderAttack;
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
    TerritoryName getTerritoryName(){return name;}
    Continent getContinent(){return continent;}
    int getNumArmies(){return numArmies;}
    Player getOwner(){return owner;}
    public String[] getAdjacentTerritories(){return adjacentTerritories;}
    public void printAdjacentTerritories(){
        String divider = "";
        for (String x : adjacentTerritories) {
            System.out.print(divider + x);
            divider = " | ";
        }
        System.out.println("\n------------------------------------");
    }
    public void listTerritoryInfo(){
//        cleaner format testing
//        System.out.println("-----------------------------------------------------------------------------------------------");
//        System.out.printf("%13s %24s %15s %15s %22s\n","Name","Continent","Owner","# of Armies","Adjacent Territories");
//        System.out.printf("%13s %24s %15s %15d\n", name, continent, owner.getPlayerName(), numArmies);
//      
        System.out.println("-----------------------------------------------------------------------------------------------\n");
        System.out.println(getTerritoryName()+":");
        System.out.println("\tOwner: " + getOwner().getPlayerName());
        System.out.println("\tNumber of armies: " + getNumArmies());
        System.out.println("\tContinent: " + getContinent());
        System.out.print("\tAdjacent territories: ");
            printAdjacentTerritories();
    }
    public String getTerritoryInfo()
    {
        return (getTerritoryName()+":\n" + "\tOwner: " + getOwner().getPlayerName() + "\n\tNumber of armies: " + getNumArmies() + "\n\tContinent: " + getContinent() + "\n");
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
