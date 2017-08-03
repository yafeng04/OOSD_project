package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yangfeng on 30/3/17.
 */
public class TurnManager implements TurnManagerInterface {

    int turnCount = 0;
    int playerNum;
    ArrayList<Player> players = new ArrayList<Player>();

    public TurnManager(int num) {

        this.playerNum = num;
        for (int i = 0; i < num; i++) {
            players.add(new Player(i + 1));
        }

        int i = new Random().nextInt(playerNum);
        players.get(i).setIdentity("saboteur");

        int j=new Random().nextInt(playerNum);
        while (j==i){
            j=new Random().nextInt(playerNum);
        }
        players.get(j).setIdentity("gold digger");

        for (int k =0;k<players.size();k++){
            if(players.get(k).getIdentity()==null){
                int newNum=new Random().nextInt(2);
                if(newNum==0){
                    players.get(k).setIdentity("saboteur");
                }
                else players.get(k).setIdentity("gold digger");
            }
        }


    }

    public int getPlayerNum() {
        return playerNum;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


}
