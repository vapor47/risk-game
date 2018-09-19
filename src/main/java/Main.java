import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    // Key = Player name; Value = Player object
    // Does not include neutral in 2 player games
    static Map<String, Player> playerMap = new HashMap<String, Player>();

    // Holds Player names and maintains turn order
    static ArrayList<String> playerList = new ArrayList<String>();

    public static void main(String[] args){
        Setup setup = new Setup();

//        while(true){ //until only 1 player occupies territories(except for neutral in 2 player games)
//            /*
//            cycle through player turns
//             */
//            return;
//        }
    }
}