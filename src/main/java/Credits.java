import java.util.Scanner;

public class Credits 
{
	private int credits = 0;
	
	public int getCredits()
	{
		return credits;
	}
	
	public void addCredits(int cred)
	{		
		if(cred >= 0)
			credits += cred;
	}
	
	public void removeCredits(int cred)
	{
		if(credits > 0 && credits > cred && cred > 0)
			credits -= cred;
	}

	public void giveCredits(Player otherPlayer)
	{
		if(credits > 0)
		{
			int transfer = 0;
			Scanner input = new Scanner(System.in);
			
			System.out.println("How many credits would you like to give?");
			
			for(transfer = input.nextInt(); transfer <= 0 && transfer > credits; transfer = input.nextInt())
				System.out.printf("%d is not a valid number of credits to transfer(1 - %d)%n", transfer, credits);
			
			input.close();
			
			credits -= transfer;
			otherPlayer.credits.addCredits(transfer);
		}
	}
}
