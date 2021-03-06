import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

public class StoreTest {

	@Test
	public void testPurchaseService() 
	{            
		Bank bank = new Bank();
		Store store = new Store();
		Player player = new Player("player0");
		ByteArrayInputStream in;

		for(int i = 2; i <= 4; i++)
		{
			bank.addAccount(player.getPlayerName());
			player.credits.addCredits(20);
			in = new ByteArrayInputStream(Integer.toString(i).getBytes());
			System.setIn(in);
			store.purchaseService(player);
			player = new Player("player" + Integer.toString(i));
		}
	
	}

	@Test
	public void testOverPurchase()
	{
		Store store = new Store();
		Player player = new Player("player");
		Bank bank = new Bank();
		bank.addAccount(player.getPlayerName());
		ByteArrayInputStream in;
		player.credits.addCredits(20);
		for(int i = 0; i < 2; i++)
		{
			in = new ByteArrayInputStream("3".getBytes());
			System.setIn(in);
			store.purchaseService(player);
		}
		
	}

}
