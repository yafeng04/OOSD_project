package Model;


/**
 * Created by sephyroth on 28/05/2017.
 */
public abstract class PlayerDecorator implements PlayerInterface{
    protected PlayerInterface decoratedPlayer;

    public PlayerDecorator(PlayerInterface decoratedPlayer){
        this.decoratedPlayer = decoratedPlayer;
    }
}
