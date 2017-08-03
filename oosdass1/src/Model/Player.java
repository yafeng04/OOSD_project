package Model;

import java.util.ArrayList;

/**
 * Created by yangfeng on 1/4/17.
 */
public class Player implements PlayerInterface{

    private PlayerState ps=null;
    private int score;
    private boolean giveup=false;
    private ArrayList<PieceType> handCards=new ArrayList<>();
    private int id;
    private boolean undo=false;
    private String identity=null;

    public Player(int id) {
        this.id = id;
        handCards=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            handCards.add(PieceType.PATH0);
        }
    }

    public ArrayList<PieceType> getHandCards() {
        return handCards;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public PlayerState getPs() {
        return ps;
    }

    public void setPs(PlayerState ps) {
        this.ps = ps;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }


}

