import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class TelegramBot extends TelegramLongPollingBot
{
	private static boolean botEnabled = false;
	private static TelegramBot INSTANCE = null;

    public static TelegramBot getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TelegramBot();
        }
        return INSTANCE;
    }

	private static Update sharedUpdate;
	private static Map<Player, Long> players = new HashMap<Player, Long>();
	private static String currentMessage;	
	@Override
	public String getBotToken()
	{
		return "707891995:AAEPU_fBp3dcmgGOkndtc6qQ3l_cpU39I-w";
		
	}

	@Override
	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		SendMessage message = new SendMessage();
		sharedUpdate = update;
		currentMessage = update.getMessage().toString();
		message.setChatId(update.getMessage().getChatId());			
		message.setText(commands(update));
		
		try {
			execute(message);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Im reading");
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "bhbb_bot";
	}
	
	public void setPlayerName(HashMap<String, Player> playerMap)
	{
		SendMessage message = new SendMessage();
		int i = 0;
		while(i < playerMap.size())
		{
			if(sharedUpdate.getMessage().getChat().toString().equals("/join")&& !players.containsValue(sharedUpdate.getMessage().getChatId()))
			{
				message.setChatId(players.get(playerMap.get("Player "+ Integer.toString(i))));
				if(!players.putIfAbsent(playerMap.get("Player "+ Integer.toString(i)), sharedUpdate.getMessage().getChatId()).equals(null))
					 message.setText("This player has allready give a name");
				else
					message.setText("Player " + Integer.toString(i) + " has joined the game!");
				i++;
				try {
					execute(message);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(playerMap.size());
				
			}
		}
		

		
	}
	
	private String commands(Update update)
	{
		if(update.getMessage().getText().equals("/start"))
		{
			return "Waiting for players";
		}
		else if(update.getMessage().getText().equals("/end"))
		{
			return "No more players can be added";
		}
		else if(update.getMessage().getText().equals("/quit"))
			return "You have quit the game";
		else if(update.getMessage().getText().toCharArray()[0] == ('/'))
			return "Invalid Command";	
		return "";
	}


	
	TelegramBot(){}
	
	//TelegramBot(boolean state)
	//{	
		
	///}
	
	
	/*public void sendMessage(String message)
	{
		if(botEnabled)
		{
			
		}
		
		else
			System.out.print(message);
	}
	
	public <T> T recieveMessage(Type t)
	{
		T answer = null;
		if(botEnabled)
		{
		}
		
		else
		{	
			Scanner input = new Scanner(System.in);
			if(Integer.class.isInstance(t))
			{
				
			}
			else if(String.class.isInstance(t))
			{
				
			}
		}
		
		return answer;
	}*/

}
