package Model;

/**
 * Created by sephyroth on 28/05/2017.
 */
public class PlayerUndo extends PlayerDecorator {
    boolean undo = false;
    public PlayerUndo(PlayerInterface decoratedPlayer) {
        super(decoratedPlayer);
    }


    @Override
    public int getScore() {
        return decoratedPlayer.getScore();
    }

    @Override
    public void setScore(int score) {
        decoratedPlayer.setScore(score);
    }

    @Override
    public PlayerState getPs() {
        return decoratedPlayer.getPs();
    }

    @Override
    public void setPs(PlayerState ps) {
        decoratedPlayer.setPs(ps);
    }

    @Override
    public String getIdentity() {
        return decoratedPlayer.getIdentity();
    }

    @Override
    public void setIdentity(String identity) {
        decoratedPlayer.setIdentity(identity);
    }

    public boolean isUndo(){
        return this.undo;
    }

    public void setUndo(boolean undo){
        this.undo = undo;
    }
}
