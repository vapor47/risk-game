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
public class TelegramBot 
{
	static public class botInfo extends TelegramLongPollingBot
	{	
		Map<Long, String> messages = new HashMap<Long, String>();
		
		@Override
		public String getBotToken()
		{
			return "707891995:AAEPU_fBp3dcmgGOkndtc6qQ3l_cpU39I-w";
			
		}

		@Override
		public void onUpdateReceived(Update update) {
			// TODO Auto-generated method stub
			SendMessage message = new SendMessage();
			messages.put(update.getMessage().getChatId(), update.getMessage().toString());
			message.setChatId(update.getMessage().getChatId());
			message.setText("Message Recieved");
			System.out.println("Im reading");
		}

		@Override
		public String getBotUsername() {
			// TODO Auto-generated method stub
			return "bhbb_bot";
		}

			

			
	}
	
	
	boolean botEnabled = false;
	
	
	TelegramBot(boolean state)
	{
		botEnabled = state;
		if(botEnabled)
		{
			ApiContextInitializer.init();
	
			TelegramBotsApi riskBot = new TelegramBotsApi();
			try {
				riskBot.registerBot(new botInfo());

			} catch (TelegramApiException e) {
         	  e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(String message)
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
	}

}
