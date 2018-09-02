import java.util.Scanner;

public class Setup {

    private int numPlayers;

    public Setup(){
        Scanner input = new Scanner(System.in);
        System.out.print("Welcome to Risk!\nHow many people are playing(2-6): ");
        numPlayers = input.nextInt();
    }
}
