import java.util.Scanner;

public class PurchaseProxy
{	
	private Bank bank = new Bank();
	
	public int purchase(String user)
	{
		String instructions = "How many credits would you like to purchase?\n($1 = 1 credit)";
		System.out.println(instructions);
		
		int credits = 0;
		if(bank.showMoney(user) > 0)
		{	
			Scanner input = new Scanner(System.in);
			for(credits = input.nextInt();  credits > bank.showMoney(user); credits = input.nextInt())
				System.out.printf("You currently only have $%d, please give a valid number (1-%d)%n%s%n", bank.showMoney(user), bank.showMoney(user), instructions);
			
			input.close();
			bank.withdrawMoney(user, credits);
		}
		
		return credits;
		
		
	}
}
