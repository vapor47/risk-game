import java.util.HashMap;

public class Bank 
	{
		static HashMap<String, Integer> accounts = new HashMap<String, Integer>();	
		
		public void addAccount(String acctName)
		{
			accounts.putIfAbsent(acctName, 20);
		}
		
		public int showMoney(String acctName)
		{
			return accounts.get(acctName);
		}
		
		public void withdrawMoney(String acctName, int amount)
		{
			System.out.println("here");
			int current = accounts.get(acctName); 
			
			if(amount <= current)
				accounts.replace(acctName, (current - amount));
		}		
	}