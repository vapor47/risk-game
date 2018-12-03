import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Tweeter {

    public static void TweetTerritoriesConquered(Player player) throws Exception{
        try {
            Properties apiKeys = new Properties();
            FileInputStream in = new FileInputStream("secrets_xXBlackhatBadBoisXx.prop");
            apiKeys.load(in);

            Twitter twitter = new TwitterFactory().getInstance();

            twitter.setOAuthConsumer(apiKeys.getProperty("consumerKeyStr"), apiKeys.getProperty("consumerSecretStr"));
            AccessToken accessToken = new AccessToken(apiKeys.getProperty("accessTokenStr"), apiKeys.getProperty("accessTokenSecretStr"));

            twitter.setOAuthAccessToken(accessToken);
            twitter.updateStatus(player.getPlayerName() + " has conquered " + player.territoriesConquered() + " territories this turn!");

            System.out.println("Successfully updated the status in Twitter.");
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }

    public static void TweetEndOfGame(HashMap<String,Player> players) throws Exception{
        try {
            Properties apiKeys = new Properties();
            FileInputStream in = new FileInputStream("secrets_xXBlackhatBadBoisXx.prop");
            apiKeys.load(in);

            Twitter twitter = new TwitterFactory().getInstance();

            twitter.setOAuthConsumer(apiKeys.getProperty("consumerKeyStr"), apiKeys.getProperty("consumerSecretStr"));
            AccessToken accessToken = new AccessToken(apiKeys.getProperty("accessTokenStr"), apiKeys.getProperty("accessTokenSecretStr"));

            twitter.setOAuthAccessToken(accessToken);
            StringBuilder sb = new StringBuilder();
            sb.append("Territories captured per player: \n");
            for(Map.Entry<String,Player> player: players.entrySet()){
                sb.append(player.getValue().getPlayerName()).append(": ").append(player.getValue().getTerritoryList().getTerritoryCount()).append("\n");
            }
            twitter.updateStatus(sb.toString());

            System.out.println("Successfully updated the status in Twitter.");
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}
