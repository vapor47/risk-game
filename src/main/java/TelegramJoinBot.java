import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class TelegramJoinBot extends AbilityBot {

    private static final String BOT_TOKEN = "629312074:AAGGaDuYr8LLAjWuKUREL3PtAB_dOC7qUqs";
    private static final String BOT_USERNAME = "RiskBot";

    private String gameId;
    public String getGameId() { return gameId; }
    public void setGameId(String gameId) { this.gameId = gameId; }

    private static TelegramJoinBot INSTANCE = null;
    public static TelegramJoinBot getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TelegramJoinBot(BOT_TOKEN, BOT_USERNAME);
        }
        return INSTANCE;
    }

    private TelegramJoinBot(String token, String username) {
        super(token, username);
    }

    @Override
    public int creatorId() {
        return 743262915;
    }

    /*
    @Override
    public void onUpdatesReceived(List<Update> updates) {

    }

    @Override
    public void onClosing() {

    }
    */

    public Ability join() {
        return Ability
                .builder()
                .name("join")
                .info("joins a game with the given game ID")
                .input(1)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    if(ctx.firstArg().equals(gameId)){
                        
                    }
                    silent.send("Hello World", ctx.chatId());
                })
                .build();
    }
}
/*
check if matches gameId
    send you have joined the game, or that is not a valid game ID
    add them to game
 */
