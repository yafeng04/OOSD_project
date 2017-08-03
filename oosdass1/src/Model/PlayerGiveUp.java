package Model;

/**
 * Created by sephyroth on 28/05/2017.
 */
public class PlayerGiveUp extends PlayerDecorator {
    boolean giveUp = false;
    public PlayerGiveUp(PlayerInterface decoratedPlayer) {
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

    public boolean isGiveUp(){
        return this.giveUp;
    }

    public void setGiveUp(boolean giveUp){
        this.giveUp = giveUp;
    }
}
