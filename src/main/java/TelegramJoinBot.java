import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;

import java.util.HashMap;

public class TelegramJoinBot extends AbilityBot {

    private static final String BOT_TOKEN = "629312074:AAGGaDuYr8LLAjWuKUREL3PtAB_dOC7qUqs";
    private static final String BOT_USERNAME = "RiskBot";

    private String gameId;
    public String getGameId() { return gameId; }
    public void setGameId(String gameId) { this.gameId = gameId; }

    public HashMap<Player, Long> playerChatIDs = new HashMap<>();

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

    private int playerCount = 0;
    private int maxPlayers = 3;
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
                        if(playerCount < maxPlayers) {
                            Player player = new Player(ctx.user().getFirstName());
                            playerChatIDs.put(player, ctx.chatId());
                            playerCount++;
                            silent.send("You have joined the game!", ctx.chatId());
                            silent.send("Number of players joined: " + playerCount, ctx.chatId());
                        } else {
                            silent.send("Sorry, this game is full.", ctx.chatId());
                        }
                    } else {
                        silent.send("Sorry, that is not a valid game ID.", ctx.chatId());
                    }
                })
                .build();
    }

    public Ability navy() {
        String string = "What the fuck did you just fucking say about me, you little bitch?" +
                "I’ll have you know I graduated top of my class in the Navy Seals, and I’ve been involved in numerous secret raids on Al-Quaeda," +
                "and I have over 300 confirmed kills." +
                "I am trained in gorilla warfare and I’m the top sniper in the entire US armed forces." +
                "You are nothing to me but just another target." +
                "I will wipe you the fuck out with precision the likes of which has never been seen before on this Earth, mark my fucking words." +
                "You think you can get away with saying that shit to me over the Internet? Think again, fucker." +
                "As we speak I am contacting my secret network of spies across the USA and" +
                "your IP is being traced right now so you better prepare for the storm, maggot." +
                "The storm that wipes out the pathetic little thing you call your life. You’re fucking dead, kid." +
                "I can be anywhere, anytime, and I can kill you in over seven hundred ways, and that’s just with my bare hands." +
                "Not only am I extensively trained in unarmed combat, but I have access to the entire arsenal of the United States Marine Corps" +
                "and I will use it to its full extent to wipe your miserable ass off the face of the continent, you little shit." +
                "If only you could have known what unholy retribution your little “clever” comment was about to bring down upon you," +
                "maybe you would have held your fucking tongue. But you couldn’t, you didn’t, and now you’re paying the price, you goddamn idiot." +
                "I will shit fury all over you and you will drown in it. You’re fucking dead, kiddo.";
        return Ability
                .builder()
                .name("navy")
                .info("what did you say?")
                .input(0)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    silent.send(string, ctx.chatId());
                })
                .build();
    }

}
/*
check if matches gameId
    send you have joined the game, or that is not a valid game ID
    add them to game
 */
