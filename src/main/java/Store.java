import java.util.Scanner;

public class Store 
{   
	private int serviceSelect()
	{
		String serviceMenu = "Please select what you want to buy(1-4)\n"
				 + "1.Credits\n"
				 + "2.Draw Card\n"
				 + "3.Undo Action\n"
				 + "4.Player Credit Transfer";
		
		System.out.println(serviceMenu);
		int select;
		Scanner input = new Scanner(System.in);
				
		for(select = input.nextInt(); select < 1 && select > 4; select = input.nextInt())
			System.out.printf("%d is not a valid option%n%s%n", select, serviceMenu);
		//input.close();
		return select;
	}
	
	public String purchaseService(Player player)
	{
		String service = null;
		int serviceNum = serviceSelect();
		PurchaseProxy pp = new PurchaseProxy();
		switch(serviceNum)
		{
			case 1: service = "Credits";
					player.credits.addCredits(pp.purchase(player.getPlayerName()));
					break;
					
			case 2: service = "Draw Card";
					player.credits.removeCredits(5);
					break;
					
			case 3: service = "Undo Action"; //Currently unsure how to implement this.
					player.credits.removeCredits(15);
					break;
			
			case 4: service = "Player Credit Transfer";
					break;
		}
		
		return service;
		
	}
}
