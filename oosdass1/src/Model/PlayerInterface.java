package Model;

/**
 * Created by sephyroth on 28/05/2017.
 */
public interface PlayerInterface {
    // get player score
    int getScore();

    // set player score
    void setScore(int score);

//     get player state
    PlayerState getPs();

    // set player state
    void setPs(PlayerState ps);


    // get player's identity
    String getIdentity();

    // set player's identity
    void setIdentity(String identity);

}
